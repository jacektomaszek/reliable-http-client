/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rhttpc.client

import scala.concurrent.{ExecutionContext, Future}

trait PublicationHandler[Result] {

  private[client] def run(): Unit

  private[client] def beforePublication(correlationId: String): Unit

  private[client] def processPublicationAck(correlationId: String, ack: Future[Unit])
                                           (implicit ec: ExecutionContext): Result

  private[client] def stop()(implicit ec: ExecutionContext): Future[Unit]

}

object StraightforwardPublicationHandler extends PublicationHandler[Future[Unit]] {
  override private[client] def run(): Unit = {}

  override private[client] def beforePublication(correlationId: String): Unit = {}

  override private[client] def processPublicationAck(correlationId: String, ack: Future[Unit])
                                                    (implicit ec: ExecutionContext): Future[Unit] = ack

  override private[client] def stop()(implicit ec: ExecutionContext): Future[Unit] = Future.successful(Unit)
}