//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.plexpt.chatgpt.entity.billing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {
  @JsonProperty("prompt_tokens")
  private long promptTokens;
  @JsonProperty("completion_tokens")
  private long completionTokens;
  @JsonProperty("total_tokens")
  private long totalTokens;

  public Usage() {
  }

  public long getPromptTokens() {
    return this.promptTokens;
  }

  public long getCompletionTokens() {
    return this.completionTokens;
  }

  public long getTotalTokens() {
    return this.totalTokens;
  }

  @JsonProperty("prompt_tokens")
  public void setPromptTokens(long promptTokens) {
    this.promptTokens = promptTokens;
  }

  @JsonProperty("completion_tokens")
  public void setCompletionTokens(long completionTokens) {
    this.completionTokens = completionTokens;
  }

  @JsonProperty("total_tokens")
  public void setTotalTokens(long totalTokens) {
    this.totalTokens = totalTokens;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof Usage)) {
      return false;
    } else {
      Usage other = (Usage)o;
      if (!other.canEqual(this)) {
        return false;
      } else if (this.getPromptTokens() != other.getPromptTokens()) {
        return false;
      } else if (this.getCompletionTokens() != other.getCompletionTokens()) {
        return false;
      } else {
        return this.getTotalTokens() == other.getTotalTokens();
      }
    }
  }

  protected boolean canEqual(Object other) {
    return other instanceof Usage;
  }

  public int hashCode() {
    int result = 1;
    long $promptTokens = this.getPromptTokens();
    result = result * 59 + (int)($promptTokens >>> 32 ^ $promptTokens);
    long $completionTokens = this.getCompletionTokens();
    result = result * 59 + (int)($completionTokens >>> 32 ^ $completionTokens);
    long $totalTokens = this.getTotalTokens();
    result = result * 59 + (int)($totalTokens >>> 32 ^ $totalTokens);
    return result;
  }

  public String toString() {
    return "Usage(promptTokens=" + this.getPromptTokens() + ", completionTokens=" + this.getCompletionTokens() + ", totalTokens=" + this.getTotalTokens() + ")";
  }
}
