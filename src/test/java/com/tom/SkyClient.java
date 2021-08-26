/*
 * Copyright 2015 The gRPC Authors
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

package com.tom;

import com.tom.service.grpc.MetricExportServiceGrpc;
import com.tom.service.grpc.SubscriptionReq;
import com.tom.service.grpc.SubscriptionsResp;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SkyClient {
    private static final Logger logger = Logger.getLogger(SkyClient.class.getName());

    private final MetricExportServiceGrpc.MetricExportServiceBlockingStub blockingStub;

    /** Construct client for accessing HelloWorld server using the existing channel. */
    public SkyClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = MetricExportServiceGrpc.newBlockingStub(channel);
    }

    Set<String> subscriptionSet= new HashSet<>();
    /** Say hello to server. */
    public void sub() {
        SubscriptionsResp response;
        try {
            response =
                    blockingStub.withDeadlineAfter(10, TimeUnit.SECONDS)
                    .subscription(SubscriptionReq.newBuilder().build());
            response.getMetricNamesList().forEach(subscriptionSet::add);
            logger.log(Level.INFO,"Get exporter subscription list, {}", subscriptionSet);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMetricNamesList());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        String target = "localhost:6565";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext(true)
                .build();
        try {
            SkyClient client = new SkyClient(channel);
            client.sub();
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
