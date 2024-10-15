package woaini.fenger.bot.code.impl.qq.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class QQBOTWsDTO implements Serializable {

  /**
   * op : 0   https://bot.q.qq.com/wiki/develop/api/gateway/opcode.html 消息含义
   * d : {} 代表事件内容
   * s : 42 下行消息都会有一个序列号，标识消息的唯一性，客户端需要再发送心跳的时候，携带客户端收到的最新的
   * t : GATEWAY_EVENT_NAME  代表事件类型
   */
  private int op;
  private JSONObject d;
  private int s;
  private String t;

  public static void main(String[] args){
    String s = """
        {
          "op": 2,
          "d": {
            "token": "my_token",
            "intents": 513,
            "shard": [0, 4],
            "properties": {
              "$os": "linux",
              "$browser": "my_library",
              "$device": "my_library"
            }
          }
        }
      """;

    QQBOTWsDTO dto = JSONObject.parseObject(s, QQBOTWsDTO.class);

    System.out.println(JSONObject.toJSONString(dto));
  }
}
