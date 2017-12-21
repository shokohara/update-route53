package com

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.route53.model._
import com.amazonaws.services.route53.{AmazonRoute53, AmazonRoute53ClientBuilder}

import scala.collection.convert.ImplicitConversions._
import scala.sys.process._
import scala.util.Try

object Main {

  val command = "dig +short myip.opendns.com @resolver1.opendns.com"

  def main(args: Array[String]): Unit = {
    val config = MyConfig.loadConfig.fold(throw _, identity)
    Using(AmazonRoute53ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(config.credential.accessKeyId, config.credential.secretAccessKey))).withRegion(config.credential.region).build()) { client: AmazonRoute53 =>
        val ip = Try(command.!!).toEither.fold(throw _, identity)
        val changes = config.route53.recordSets.map { case (name, rrType) =>
          new Change(ChangeAction.UPSERT, new ResourceRecordSet(name, rrType).withResourceRecords(new ResourceRecord(ip)).withTTL(60L))
        }
        client.changeResourceRecordSets(new ChangeResourceRecordSetsRequest(config.route53.hostedZoneId, new ChangeBatch(changes.toList)))
    }
  }
}
