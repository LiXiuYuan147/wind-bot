package woaini.fenger.bot.core.jpa.convert;

import cn.hutool.core.util.StrUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Converter
public class StringListConverter implements AttributeConverter<Set<String>, String>, Serializable {

  private static final long serialVersionUID = -5582233087319897500L;

  public StringListConverter() {}

  public String convertToDatabaseColumn(Set<String> list) {
    return StrUtil.join(",", list);
  }

  public Set<String> convertToEntityAttribute(String dbData) {
    if (StrUtil.isEmpty(dbData)){
      return new HashSet<>(0);
    }
    return new HashSet<>(StrUtil.split(dbData, ","));
  }
}
