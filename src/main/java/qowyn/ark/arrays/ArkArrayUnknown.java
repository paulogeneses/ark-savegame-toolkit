package qowyn.ark.arrays;

import java.util.AbstractList;
import java.util.Base64;
import java.util.Set;

import javax.json.JsonString;
import javax.json.JsonValue;

import qowyn.ark.ArkArchive;
import qowyn.ark.json.SimpleJsonString;

public class ArkArrayUnknown extends AbstractList<Byte> implements ArkArray<Byte> {

  private static final Base64.Decoder DECODER = Base64.getDecoder();

  private static final Base64.Encoder ENCODER = Base64.getEncoder();

  private final byte[] value;

  public ArkArrayUnknown(ArkArchive archive, int size) {
    value = archive.getBytes(size);
  }

  public ArkArrayUnknown(JsonValue v) {
    value = DECODER.decode(((JsonString)v).getString());
  }

  @Override
  public Class<Byte> getValueClass() {
    return Byte.class;
  }

  @Override
  public int calculateSize(boolean nameTable) {
    return value.length;
  }

  @Override
  public JsonValue toJson() {
    return new SimpleJsonString(ENCODER.encodeToString(value));
  }

  @Override
  public void write(ArkArchive archive) {
    archive.putBytes(value);
  }

  @Override
  public void collectNames(Set<String> nameTable) {}

  @Override
  public Byte get(int index) {
    if (index < 0 || index >= value.length) {
      throw new IndexOutOfBoundsException();
    }

    return Byte.valueOf(value[index]);
  }

  public byte getPrimitive(int index) {
    if (index < 0 || index >= value.length) {
      throw new IndexOutOfBoundsException();
    }

    return value[index];
  }

  @Override
  public int size() {
    return value.length;
  }

}
