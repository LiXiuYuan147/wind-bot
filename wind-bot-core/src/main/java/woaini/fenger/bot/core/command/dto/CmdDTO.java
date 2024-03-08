package woaini.fenger.bot.core.command.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * CMD DTO
 *
 * @see CmdDTO
 * @author yefeng {@code @Date} 2023-05-16 16:50:39
 */
@Data
public class CmdDTO {

  /**
   * @see String 主命令
   */
  String mainCmd;

  /**
   * @see String Subcmd subParams第一个就是子命令
   */
  String subCmd;

  /**
   * @see String 子参数 就是不能被命令识别的
   */
  List<String> subParams;

  /**
   * @see Map<String, String> 帕拉姆斯
   */
  Map<String, String> params;

  public boolean help() {
    return params.containsKey("h") || params.containsKey("help");
  }
}
