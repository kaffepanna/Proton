#ifndef _PROTON_FRAMING_H
#define _PROTON_FRAMING_H 1

/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

#include <stdint.h>
#include <sys/types.h>

#define AMQP_HEADER_SIZE (8)

typedef struct {
  uint8_t type;
  uint16_t channel;
  size_t ex_size;
  char *extended;
  size_t size;
  char *payload;
} pn_frame_t;

size_t pn_read_frame(pn_frame_t *frame, char *bytes, size_t available);
size_t pn_write_frame(char *bytes, size_t size, pn_frame_t frame);

#endif /* framing.h */
