package com

import com.amazonaws.services.route53.AmazonRoute53

case class Closer[-A](close: A => Unit)

object Closer {
  implicit val writerCloser: Closer[AmazonRoute53] = Closer(_.shutdown())
}
