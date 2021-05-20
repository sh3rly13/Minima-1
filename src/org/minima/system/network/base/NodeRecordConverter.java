/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.minima.system.network.base;

import static org.minima.system.network.base.DiscoveryNetwork.ATTESTATION_SUBNET_ENR_FIELD;
import static org.minima.system.network.base.DiscoveryNetwork.ETH2_ENR_FIELD;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.ethereum.beacon.discovery.schema.EnrField;
import org.ethereum.beacon.discovery.schema.NodeRecord;
import org.minima.system.network.base.peer.DiscoveryPeer;
//import org.minima.system.network.base.EnrForkId;
import org.minima.system.network.base.ssz.SszBitvector;

public class NodeRecordConverter {
  private static final Logger LOG = LogManager.getLogger(NodeRecordConverter.class);

  static Optional<DiscoveryPeer> convertToDiscoveryPeer(final NodeRecord nodeRecord) {
    return nodeRecord
        .getTcpAddress()
        .map(address -> socketAddressToDiscoveryPeer(nodeRecord, address));
  }

  private static DiscoveryPeer socketAddressToDiscoveryPeer(
      final NodeRecord nodeRecord, final InetSocketAddress address) {

    final Optional<EnrForkId> enrForkId =
        parseField(nodeRecord, ETH2_ENR_FIELD, EnrForkId.SSZ_SCHEMA::sszDeserialize);

    final SszBitvector persistentSubnets =
        parseField(
                nodeRecord,
                ATTESTATION_SUBNET_ENR_FIELD,
                DiscV5Service.SUBNET_SUBSCRIPTIONS_SCHEMA::fromBytes)
            .orElse(DiscV5Service.SUBNET_SUBSCRIPTIONS_SCHEMA.getDefault());

            
            Bytes nodeId =  nodeRecord.getNodeId();
            String enr        =  nodeRecord.asEnr();
    return new DiscoveryPeer(
        ((Bytes) nodeRecord.get(EnrField.PKEY_SECP256K1)), address, enrForkId, persistentSubnets, nodeId, enr);
  }

  private static <T> Optional<T> parseField(
      final NodeRecord nodeRecord, final String fieldName, final Function<Bytes, T> parse) {
    try {
      return Optional.ofNullable((Bytes) nodeRecord.get(fieldName)).map(parse);
    } catch (final Exception e) {
      LOG.debug("Failed to parse ENR field {}", fieldName, e);
      return Optional.empty();
    }
  }
}
