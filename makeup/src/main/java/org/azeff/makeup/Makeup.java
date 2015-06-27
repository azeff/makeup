package org.azeff.makeup;

import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

public class Makeup {

  private SpannableStringBuilder builder;

  private Makeup() {
    builder = new SpannableStringBuilder();
  }

  public static Makeup create() {
    return new Makeup();
  }

  public Makeup append(@Nullable Makeup makeup) {
    return insert(makeup, length());
  }

  public Makeup append(@Nullable Spannable formatted) {
    return insert(formatted, length());
  }

  public Makeup prepend(@Nullable Makeup makeup) {
    return insert(makeup, 0);
  }

  public Makeup prepend(@Nullable Spannable formatted) {
    return insert(formatted, 0);
  }

  public Makeup insert(@Nullable Makeup makeup, int position) {
    if (makeup == null) {
      return this;
    }

    return insert(makeup.apply(), position);
  }

  public Makeup insert(@Nullable Spannable formatted, int position) {
    if (formatted == null) {
      return this;
    }

    return this;
  }

  public PartialMakeup append(@Nullable String text) {
    return insert(text, length());
  }

  public PartialMakeup appendLine(@Nullable String text) {
    return insertLine(text, length());
  }

  public PartialMakeup prepend(@Nullable String text) {
    return insert(text, 0);
  }

  public PartialMakeup prependLine(@Nullable String text) {
    return insertLine(text, 0);
  }

  public PartialMakeup insert(@Nullable String text, int position) {
    builder.insert(position, text);
    return new PartialMakeup(position, text.length());
  }

  public PartialMakeup insertLine(@Nullable String text, int position) {
    return insert(text, position).line();
  }

  public Makeup line() {
    builder.append("\n");
    return this;
  }

  public AllMakeup all() {
    return new AllMakeup();
  }

  public Spannable apply() {
    return builder;
  }

  public int length() {
    return builder.length();
  }

  public class PartialMakeup {

    private CommonPartMakeup makeup;

    public PartialMakeup(int start, int length) {
      SpannableStringBuilder builder = Makeup.this.builder;
      makeup = new CommonPartMakeup(builder, start, length);
    }

    public PartialMakeup bold() {
      makeup.bold();
      return this;
    }

    public PartialMakeup italic() {
      makeup.italic();
      return this;
    }

    public PartialMakeup underline() {
      makeup.underline();
      return this;
    }

    public PartialMakeup strikethrough() {
      makeup.strikethrough();
      return this;
    }

    public PartialMakeup color(@ColorInt int color) {
      makeup.color(color);
      return this;
    }

    public PartialMakeup bg(@ColorInt int color) {
      makeup.bg(color);
      return this;
    }

    public PartialMakeup proportion(
        @FloatRange(from = 0.0, fromInclusive = false) float proportion) {
      makeup.proportion(proportion);
      return this;
    }

    public PartialMakeup line() {
      builder.append("\n");
      return this;
    }

    public PartialMakeup append(@Nullable String text) {
      makeup.invalidate();
      return Makeup.this.append(text);
    }

    public AllMakeup all() {
      makeup.invalidate();
      return Makeup.this.all();
    }

    public Spannable apply() {
      makeup.invalidate();
      return Makeup.this.apply();
    }
  }

  public class AllMakeup {

    private CommonPartMakeup makeup;

    public AllMakeup() {
      SpannableStringBuilder builder = Makeup.this.builder;
      makeup = new CommonPartMakeup(builder, 0, builder.length());
    }

    public AllMakeup bold() {
      makeup.bold();
      return this;
    }

    public AllMakeup italic() {
      makeup.italic();
      return this;
    }

    public AllMakeup underline() {
      makeup.underline();
      return this;
    }

    public AllMakeup strikethrough() {
      makeup.strikethrough();
      return this;
    }

    public AllMakeup color(@ColorInt int color) {
      makeup.color(color);
      return this;
    }

    public AllMakeup bg(@ColorInt int color) {
      makeup.bg(color);
      return this;
    }

    public AllMakeup proportion(
        @FloatRange(from = 0.0, fromInclusive = false) float proportion) {
      makeup.proportion(proportion);
      return this;
    }

    public PartialMakeup append(@Nullable String text) {
      makeup.invalidate();
      return Makeup.this.append(text);
    }

    public Makeup line() {
      makeup.invalidate();
      return Makeup.this.line();
    }

    public Spannable apply() {
      makeup.invalidate();
      return Makeup.this.apply();
    }
  }

  private class CommonPartMakeup {

    private boolean invalidated = false;
    private int start;
    private int length;
    private SpannableStringBuilder builder;

    public CommonPartMakeup(SpannableStringBuilder builder, int start, int length) {
      this.builder = builder;
      this.start = start;
      this.length = length;
    }

    public CommonPartMakeup bold() {
      final StyleSpan span = new StyleSpan( Typeface.BOLD );
      setSpan(span);
      return this;
    }

    public CommonPartMakeup italic() {
      final StyleSpan span = new StyleSpan( Typeface.ITALIC );
      setSpan(span);
      return this;
    }

    public CommonPartMakeup underline() {
      final UnderlineSpan span = new UnderlineSpan();
      setSpan(span);
      return this;
    }

    public CommonPartMakeup strikethrough() {
      final StrikethroughSpan span = new StrikethroughSpan();
      setSpan(span);
      return this;
    }

    public CommonPartMakeup color(@ColorInt int color) {
      final ForegroundColorSpan span = new ForegroundColorSpan( color );
      setSpan(span);
      return this;
    }

    public CommonPartMakeup bg(@ColorInt int color) {
      final BackgroundColorSpan span = new BackgroundColorSpan( color );
      setSpan(span);
      return this;
    }

    public CommonPartMakeup proportion(
        @FloatRange(from = 0.0, fromInclusive = false) float proportion) {
      final RelativeSizeSpan span = new RelativeSizeSpan( proportion );
      setSpan(span);
      return this;
    }

    protected void invalidate() {
      invalidated = true;
    }

    protected void setSpan(Object span) {
      if (invalidated) {
        throw new IllegalStateException("is invalidated");
      }

      builder.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
  }
}
