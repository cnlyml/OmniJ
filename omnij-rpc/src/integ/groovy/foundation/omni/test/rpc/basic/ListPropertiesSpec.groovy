package foundation.omni.test.rpc.basic

import foundation.omni.BaseRegTestSpec
import foundation.omni.CurrencyID
import foundation.omni.Ecosystem
import spock.lang.Ignore

import static foundation.omni.CurrencyID.MSC
import static foundation.omni.CurrencyID.TMSC
import foundation.omni.rpc.SmartPropertyListInfo

/**
 * Specification for {@code "omni_listproperties"}.
 */
@Ignore("due to incompatibility with Omni Core 0.0.10")
class ListPropertiesSpec extends BaseRegTestSpec {

    def "Returns a property list with correct MSC and TMSC entries"() {
        when: "we get a list of properties"
        List<SmartPropertyListInfo> properties = omniListProperties()

        then: "we get a list of size >= 2"
        properties != null
        properties.size() >= 2

        when: "we convert the list to a map"
        // This may be unnecessary if we can assume the property list is ordered by propertyid
        Map<CurrencyID, SmartPropertyListInfo> props = properties.collect{[it.propertyid, it]}.collectEntries()

        then: "we can check MSC and TMSC are as expected"
        props[MSC].propertyid == MSC
        props[MSC].propertyid.ecosystem == Ecosystem.MSC
        props[MSC].name == "MasterCoin"
        props[MSC].category == "N/A"
        props[MSC].subcategory == "N/A"
        props[MSC].data == "***data***"
        props[MSC].url == "www.mastercoin.org"
        props[MSC].divisible

        props[TMSC].propertyid == TMSC
        props[TMSC].propertyid.ecosystem == Ecosystem.TMSC
        props[TMSC].name == "Test MasterCoin"
        props[TMSC].category == "N/A"
        props[TMSC].subcategory == "N/A"
        props[TMSC].data == "***data***"
        props[TMSC].url == "www.mastercoin.org"
        props[TMSC].divisible
    }
}