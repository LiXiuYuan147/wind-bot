package woaini.fenger.bot.core.event.segment.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woaini.fenger.bot.core.event.segment.Segment;

import java.io.Serializable;
@Getter
@Setter
public class Image extends Segment implements Serializable {

  private Data data;

  public Image(String file) {
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
