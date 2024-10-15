package woaini.fenger.bot.core.event.segment.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woaini.fenger.bot.core.event.segment.Segment;

import java.io.Serializable;
/**
 *
 * @see  woaini.fenger.bot.core.event.segment.impl.Image
 * @author yefeng
 * {@date 2024-10-15 11:59:25}
 */@Getter
@Setter
public class Image extends Segment implements Serializable {

  private Data data;

  /**
   *
   * @param file 文件路径 网络路径
   * @author yefeng
   * {@date 2024-10-15 11:59:29}
   * @since 1.0
   *
   */public Image(String file) {
    super("image");
    this.data = new Data(file);
  }

  @NoArgsConstructor
  @lombok.Data
  public static class Data implements Serializable {

    private String file;

    public Data(String file) {
      this.file = file;
    }
  }
}
