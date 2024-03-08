import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import woaini.fenger.bot.core.event.message.impl.PrivateMessageEvent;
import woaini.fenger.bot.core.event.segment.Messages;

public class Test {

    static {
        JSON.register(Messages.class, new Messages.Deserializer());
        JSON.register(Messages.class, new Messages.Serializer());
    }
    public static void main(String[] args){
    String json =
        """
        {
            "id": "02a589ce-4393-4a2b-a67a-453c10b6e085",
            "time": 1709888657,
            "type": "message",
            "detail_type": "private",
            "sub_type": "temp",
            "self": {
                "platform": "matcha",
                "user_id": "609216758"
            },
            "user_id": "599119084",
            "message_id": "10001",
            "message": [
                {
                    "type": "text",
                    "data": {
                        "text": "#帮助"
                    }
                }
            ],
            "alt_message": "#帮助"
        }
        """;
        PrivateMessageEvent privateMessageEvent = JSONObject.parseObject(json, PrivateMessageEvent.class);
    System.out.println(1);
    }
}
