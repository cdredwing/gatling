/**
 * Copyright 2011-2012 eBusiness Information, Groupe Excilys (www.excilys.com)
 * Copyright 2012 Gilt Groupe, Inc. (www.gilt.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.gatling.http.action

import akka.actor.{ActorRef, Props}
import com.excilys.ebi.gatling.core.session._
import com.excilys.ebi.gatling.core.action.builder.ActionBuilder
import com.excilys.ebi.gatling.core.action._
import com.excilys.ebi.gatling.core.config.ProtocolConfigurationRegistry
import com.excilys.ebi.gatling.http.util.{RequestLogger, WebSocketClient}

class OpenWebSocketActionBuilder(val attributeName: String, val actionName: EvaluatableString, val fUrl: EvaluatableString, val webSocketClient: WebSocketClient, val requestLogger: RequestLogger, val next: ActorRef = null) extends ActionBuilder {
  def withNext(next: ActorRef): ActionBuilder = new OpenWebSocketActionBuilder(attributeName, actionName, fUrl, webSocketClient, requestLogger, next)

  def build(registry: ProtocolConfigurationRegistry): ActorRef = system.actorOf(Props(new OpenWebSocketAction(attributeName, actionName, fUrl, webSocketClient, requestLogger, next, registry)))
}

class SendWebSocketMessageActionBuilder(val attributeName: String, val actionName: EvaluatableString, val fMessage: EvaluatableString, val next: ActorRef = null) extends ActionBuilder {
  def withNext(next: ActorRef): ActionBuilder = new SendWebSocketMessageActionBuilder(attributeName, actionName, fMessage, next)

  def build(registry: ProtocolConfigurationRegistry): ActorRef = system.actorOf(Props(new SendWebSocketMessageAction(attributeName, actionName, fMessage, next, registry)))
}

class CloseWebSocketActionBuilder(val attributeName: String, val actionName: EvaluatableString, val next: ActorRef = null) extends ActionBuilder {
  def withNext(next: ActorRef): ActionBuilder = new CloseWebSocketActionBuilder(attributeName, actionName, next)

  def build(registry: ProtocolConfigurationRegistry): ActorRef = system.actorOf(Props(new CloseWebSocketAction(attributeName, actionName, next, registry)))
}