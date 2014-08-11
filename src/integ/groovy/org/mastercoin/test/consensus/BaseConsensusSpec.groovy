package org.mastercoin.test.consensus

import org.mastercoin.BaseMainNetSpec
import org.mastercoin.CurrencyID
import org.mastercoin.consensus.ConsensusComparison
import org.mastercoin.consensus.MasterCoreConsensusTool
import org.mastercoin.consensus.OmniwalletConsensusTool
import spock.lang.Shared
import spock.lang.Unroll

/**
 * User: sean
 * Date: 7/9/14
 * Time: 11:31 PM
 */
abstract class  BaseConsensusSpec extends BaseMainNetSpec {
    @Shared
    ConsensusComparison comparison

    void setupComparisonForCurrency(CurrencyID id) {
        def mscFetcher = new MasterCoreConsensusTool(client)
        def mscSnapshot = mscFetcher.getConsensusSnapshot(id)

        def omniFetcher = new OmniwalletConsensusTool()
        def omniSnapshot = omniFetcher.getConsensusSnapshot(id)

        comparison = new ConsensusComparison(mscSnapshot, omniSnapshot)
    }

    def "block height is the same in both snapshots"() {
        expect:
        comparison.c1.blockHeight == comparison.c2.blockHeight
    }

    @Unroll
    def "#address #entry1 == #entry2"() {
        expect:
        entry1 == entry2

        where:
        [address, entry1, entry2] << comparison
    }

}
