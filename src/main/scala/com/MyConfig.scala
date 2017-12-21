package com

import cats.data.NonEmptyList
import com.MyConfig.{CredentialConfig, Route53Config}
import com.amazonaws.regions.Regions
import com.amazonaws.services.route53.model.RRType
import pureconfig.error.ConfigReaderException
import pureconfig.{CamelCase, ConfigFieldMapping, ProductHint}
import pureconfig.module.cats._

final case class MyConfig(credential: CredentialConfig, route53: Route53Config)

object MyConfig {

  final case class CredentialConfig(region: Regions, accessKeyId: String, secretAccessKey: String)

  final case class Route53Config(recordSets: NonEmptyList[(String, RRType)], hostedZoneId: String)

  implicit def hint[T]: ProductHint[T] = ProductHint[T](ConfigFieldMapping(CamelCase, CamelCase))

  def loadConfig: Either[ConfigReaderException[MyConfig], MyConfig] =
    pureconfig.loadConfig[MyConfig].left.map(new ConfigReaderException[MyConfig](_))
}
