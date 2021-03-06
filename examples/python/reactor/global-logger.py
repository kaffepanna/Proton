#!/usr/bin/python
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

import time
from proton.reactor import Reactor

# Not every event goes to the reactor's event handler. If we have a
# separate handler for something like a scheduled task, then those
# events aren't logged by the logger associated with the reactor's
# handler. Sometimes this is useful if you don't want to see them, but
# sometimes you want the global picture.

class Logger:

    def on_unhandled(self, name, event):
        print "LOG:", name, event

class Task:

    def on_timer_task(self, event):
        print "Mission accomplished!"

class Program:

    def on_reactor_init(self, event):
        print "Hello, World!"
        event.reactor.schedule(0, Task())

    def on_reactor_final(self, event):
        print "Goodbye, World!"

r = Reactor(Program())

# In addition to having a regular handler, the reactor also has a
# global handler that sees every event. By adding the Logger to the
# global handler instead of the regular handler, we can log every
# single event that occurs in the system regardless of whether or not
# there are specific handlers associated with the objects that are the
# target of those events.
r.global_handler.add(Logger())
r.run()
