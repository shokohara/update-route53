package com

object Using {
  def apply[A, B](resource: A)(process: A => B)(implicit closer: Closer[A]): B =
    try {
      process(resource)
    } finally {
      closer.close(resource)
    }
}
