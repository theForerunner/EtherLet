package com.example.l.EtherLet.model;



import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.EtherLet.R;
import com.example.l.EtherLet.network.JSONParser;
import com.example.l.EtherLet.network.VolleyCallback;
import com.example.l.EtherLet.network.VolleyRequest;

import org.json.JSONObject;

import java.util.List;

public class CoinInfoList implements CoinListInterface {

    private String url = "https://data.block.cc/api/v1/price?symbol_name=bitcoin,monero,ethereum,dash,eos,bitcoin-gold,ripple,litecoin,bitcoin-cash,ethereum-classic,qtum,tron,bitcoin-cash-sv,zcash,neo,stellar,omisego,waves,trueusd,dogecoin,tether,paxos-standard,true-chain,0x,aeternity,binance-coin,mithril,cardano,zb-blockchain,mr,alibabacoin,nem,kyber-network,bgg,moeda-loyalty-points,okb,ravencoin,eosdac,iota,currency-network,aelf,ip-chain,zilliqa,nxt,ontology,playgroundz,hycon,cryptonex,gxm,filecoin,latoken,basic-attention-token,decentraland,baxs,vet,bytom,huobi-token,zzex,polymath-network,hshare,tch,icon,usdc,status,infinity-economics,chainlink,waltonchain,atbcoin,syscoin,tenx,bancor,ink,swisscoin,iostoken,quarkchain,gifto,cybermiles,fcc,hc,bumo,ins-ecosystem,oneroot-network,davinci-coin,lisk,nex,metaverse,cube,tezos,cryptoworld-vip,sirin-labs-token,apis,cortex,ut,pfc,haven-protocol,mobilego,bitshares,wax,factom,augur,";

    private boolean isDataInit=false;
    @Override
    public void getInfoListData(final InfoLoadDataCallBack callBack, final Context context){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, url, null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callBack.onInfoSuccess(jsonObject);
                isDataInit=true;
                refreshUrl(context);
            }

            @Override
            public void onFailure() {
                callBack.onInfoFailure();
            }
        });

    }

    private void refreshUrl(final Context context) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.GET, context.getString(R.string.get_coin_symbol_list_url), null, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                url = context.getString(R.string.get_coin_info_list_url_header) + setSubInfoUrl(JSONParser.parseJSONToNameList(jsonObject));
            }

            @Override
            public void onFailure() {
            }
        });
    }


    @Override
    public boolean isInit(){
        return isDataInit;
    }
    private String setSubInfoUrl(List<String> nameList){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            stringBuilder.append(nameList.get(i));
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    public interface InfoLoadDataCallBack {
        void onInfoSuccess(JSONObject jsonObject);
        void onInfoFailure();
    }
}


