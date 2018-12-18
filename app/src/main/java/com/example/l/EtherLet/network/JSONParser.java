package com.example.l.EtherLet.network;

import android.util.Log;

import com.example.l.EtherLet.model.CoinInfo;
import com.example.l.EtherLet.model.dto.CommentDTO;
import com.example.l.EtherLet.model.dto.FriendDTO;
import com.example.l.EtherLet.model.dto.PostDTO;
import com.example.l.EtherLet.model.dto.UserDTO;
import com.example.l.EtherLet.model.WalletModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    private static int length=150;

    public static List<PostDTO> parseJsonToPostList(JSONObject jsonObject) {
        List<PostDTO> postDTOList = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray postArray = data.getJSONArray("list");
            for (int i = 0; i < postArray.length(); i++) {
                JSONObject postObject = postArray.getJSONObject(i);
                Log.i("DT", postObject.toString());
                JSONObject userObject = postObject.getJSONObject("postCreator");
                PostDTO postDTO = new PostDTO(postObject.getInt("postId"), postObject.getString("postTitle"), new UserDTO(userObject.getInt("userId"), userObject.getString("userUsername")), postObject.getString("postContent"), new Timestamp(postObject.getLong("postTime")));
                postDTOList.add(postDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("DT", "Length of postDTOList: " + postDTOList.size());
        return postDTOList;

    }

    public static List<CommentDTO> parseJsonToCommentList(JSONObject jsonObject) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray commentArray = data.getJSONArray("list");
            for (int i = 0; i < commentArray.length(); i++) {
                JSONObject commentObject = commentArray.getJSONObject(i);
                Log.i("DT", commentObject.toString());
                JSONObject userObject = commentObject.getJSONObject("commentSender");
                CommentDTO commentDTO = new CommentDTO(commentObject.getInt("commentId"), commentObject.getString("commentContent"), commentObject.getInt("postId"), new UserDTO(userObject.getInt("userId"), userObject.getString("userUsername")), new Timestamp(commentObject.getLong("commentTime")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentDTOList;
    }

    public static List<FriendDTO> parseJsonToFriendList(JSONObject jsonObject) {
        List<FriendDTO> friendDTOList = new ArrayList<>();
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray friendArray = data.getJSONArray("list");
            for (int i = 0; i < friendArray.length(); i++) {
                JSONObject friendObject = friendArray.getJSONObject(i);
                Log.i("DT", friendObject.toString());
                FriendDTO friendDTO = new FriendDTO(friendObject.getInt("friendshipId"), friendObject.getInt("userId"), friendObject.getString("userUsername"), friendObject.getString("userKey"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendDTOList;
    }

    public static List<String> parseJSONToNameList(JSONObject jsonObject){
        List<String> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int i=0;i<100;i++){
                JSONObject dataObject=dataList.getJSONObject(i);
                String name=dataObject.getString("name");
                list.add(name);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<CoinInfo> parseJSONToInfoList(JSONObject jsonObject){
        List<CoinInfo> list=new ArrayList<>();
        try{

            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<100;j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                CoinInfo info=new CoinInfo();
                info.setSymbol(dataObject.getString("symbol"));
                info.setName(dataObject.getString("name"));
                info.setPriceUSD(dataObject.getString("price"));
                info.setHigh(dataObject.getString("high"));
                info.setLow(dataObject.getString("low"));
                info.setVolume(dataObject.getString("volume"));
                info.setChangeHour(dataObject.getString("change_hourly"));
                info.setChangeDaily(dataObject.getString("change_daily"));
                info.setChangeWeekly(dataObject.getString("change_weekly"));
                info.setChangeMonthly(dataObject.getString("change_monthly"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static BigDecimal parseJsonToAccountBalance(JSONObject jsonObject){
        BigDecimal balance=null;
        try{
            balance=new BigDecimal(jsonObject.getString("result")).divide(new BigDecimal(10e17));
        }catch (Exception e){
            e.printStackTrace();
        }
        return balance;
    }

    public static BigDecimal parseJsonToDollarPrice(JSONObject jsonObject){
        BigDecimal price=null;
        try{
            price=new BigDecimal(jsonObject.getString("ethusd"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return price;
    }

    public static List<WalletModel.Transaction> parseJsonToTxList(JSONObject jsonObject){
        List<WalletModel.Transaction> TxList=new ArrayList<>();
        try{
            JSONArray dataList=jsonObject.optJSONArray("result");
            for(int i=0;i<dataList.length();i++){
                WalletModel.Transaction Tx=new WalletModel.Transaction();
                JSONObject data=dataList.getJSONObject(i);
                Tx.setTimeStamp(data.getString("timeStamp"));
                Tx.setSenderAddress(data.getString("from"));
                Tx.setReceiverAddress(data.getString("to"));
                Tx.setValue(data.getString("value"));
                Tx.setStatus(data.getString("txreceipt_status"));
                TxList.add(Tx);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return TxList;
    }
}
