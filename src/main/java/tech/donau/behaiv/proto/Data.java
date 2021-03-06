// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: behaiv.proto

package tech.donau.behaiv.proto;

/**
 * <pre>
 * Data messages
 * </pre>
 *
 * Protobuf type {@code Data}
 */
public  final class Data extends
    com.google.protobuf.GeneratedMessageLite<
        Data, Data.Builder> implements
    // @@protoc_insertion_point(message_implements:Data)
    DataOrBuilder {
  private Data() {
    key_ = "";
  }
  public static final int KEY_FIELD_NUMBER = 2;
  private java.lang.String key_;
  /**
   * <code>string key = 2;</code>
   * @return The key.
   */
  @java.lang.Override
  public java.lang.String getKey() {
    return key_;
  }
  /**
   * <code>string key = 2;</code>
   * @return The bytes for key.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getKeyBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(key_);
  }
  /**
   * <code>string key = 2;</code>
   * @param value The key to set.
   */
  private void setKey(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    key_ = value;
  }
  /**
   * <code>string key = 2;</code>
   */
  private void clearKey() {
    
    key_ = getDefaultInstance().getKey();
  }
  /**
   * <code>string key = 2;</code>
   * @param value The bytes for key to set.
   */
  private void setKeyBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    key_ = value.toStringUtf8();
  }

  public static final int VALUE_FIELD_NUMBER = 3;
  private double value_;
  /**
   * <code>double value = 3;</code>
   * @return The value.
   */
  @java.lang.Override
  public double getValue() {
    return value_;
  }
  /**
   * <code>double value = 3;</code>
   * @param value The value to set.
   */
  private void setValue(double value) {
    
    value_ = value;
  }
  /**
   * <code>double value = 3;</code>
   */
  private void clearValue() {
    
    value_ = 0D;
  }

  public static tech.donau.behaiv.proto.Data parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static tech.donau.behaiv.proto.Data parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static tech.donau.behaiv.proto.Data parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static tech.donau.behaiv.proto.Data parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }
  public static Builder newBuilder(tech.donau.behaiv.proto.Data prototype) {
    return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   * <pre>
   * Data messages
   * </pre>
   *
   * Protobuf type {@code Data}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        tech.donau.behaiv.proto.Data, Builder> implements
      // @@protoc_insertion_point(builder_implements:Data)
      tech.donau.behaiv.proto.DataOrBuilder {
    // Construct using tech.donau.behaiv.proto.Data.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <code>string key = 2;</code>
     * @return The key.
     */
    @java.lang.Override
    public java.lang.String getKey() {
      return instance.getKey();
    }
    /**
     * <code>string key = 2;</code>
     * @return The bytes for key.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getKeyBytes() {
      return instance.getKeyBytes();
    }
    /**
     * <code>string key = 2;</code>
     * @param value The key to set.
     * @return This builder for chaining.
     */
    public Builder setKey(
        java.lang.String value) {
      copyOnWrite();
      instance.setKey(value);
      return this;
    }
    /**
     * <code>string key = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearKey() {
      copyOnWrite();
      instance.clearKey();
      return this;
    }
    /**
     * <code>string key = 2;</code>
     * @param value The bytes for key to set.
     * @return This builder for chaining.
     */
    public Builder setKeyBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setKeyBytes(value);
      return this;
    }

    /**
     * <code>double value = 3;</code>
     * @return The value.
     */
    @java.lang.Override
    public double getValue() {
      return instance.getValue();
    }
    /**
     * <code>double value = 3;</code>
     * @param value The value to set.
     * @return This builder for chaining.
     */
    public Builder setValue(double value) {
      copyOnWrite();
      instance.setValue(value);
      return this;
    }
    /**
     * <code>double value = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearValue() {
      copyOnWrite();
      instance.clearValue();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:Data)
  }
  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0, java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new tech.donau.behaiv.proto.Data();
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case BUILD_MESSAGE_INFO: {
          java.lang.Object[] objects = new java.lang.Object[] {
            "key_",
            "value_",
          };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0002\u0003\u0002\u0000\u0000\u0000\u0002\u0208\u0003\u0000" +
              "";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
      }
      // fall through
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        com.google.protobuf.Parser<tech.donau.behaiv.proto.Data> parser = PARSER;
        if (parser == null) {
          synchronized (tech.donau.behaiv.proto.Data.class) {
            parser = PARSER;
            if (parser == null) {
              parser =
                  new DefaultInstanceBasedParser<tech.donau.behaiv.proto.Data>(
                      DEFAULT_INSTANCE);
              PARSER = parser;
            }
          }
        }
        return parser;
    }
    case GET_MEMOIZED_IS_INITIALIZED: {
      return (byte) 1;
    }
    case SET_MEMOIZED_IS_INITIALIZED: {
      return null;
    }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:Data)
  private static final tech.donau.behaiv.proto.Data DEFAULT_INSTANCE;
  static {
    Data defaultInstance = new Data();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
      Data.class, defaultInstance);
  }

  public static tech.donau.behaiv.proto.Data getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<Data> PARSER;

  public static com.google.protobuf.Parser<Data> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

