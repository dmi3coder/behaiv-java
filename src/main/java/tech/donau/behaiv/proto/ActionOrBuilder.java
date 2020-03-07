// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: behaiv.proto

package tech.donau.behaiv.proto;

public interface ActionOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Action)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 local_id = 1;</code>
   * @return The localId.
   */
  long getLocalId();

  /**
   * <code>int64 prev_local_id = 2;</code>
   * @return The prevLocalId.
   */
  long getPrevLocalId();

  /**
   * <code>string action = 3;</code>
   * @return The action.
   */
  java.lang.String getAction();
  /**
   * <code>string action = 3;</code>
   * @return The bytes for action.
   */
  com.google.protobuf.ByteString
      getActionBytes();

  /**
   * <code>string object = 4;</code>
   * @return The object.
   */
  java.lang.String getObject();
  /**
   * <code>string object = 4;</code>
   * @return The bytes for object.
   */
  com.google.protobuf.ByteString
      getObjectBytes();

  /**
   * <code>int32 current_screen_id = 5;</code>
   * @return The currentScreenId.
   */
  int getCurrentScreenId();

  /**
   * <pre>
   *    repeated Event events = 7; TODO
   * </pre>
   *
   * <code>repeated int32 screen_stack = 6;</code>
   * @return A list containing the screenStack.
   */
  java.util.List<java.lang.Integer> getScreenStackList();
  /**
   * <pre>
   *    repeated Event events = 7; TODO
   * </pre>
   *
   * <code>repeated int32 screen_stack = 6;</code>
   * @return The count of screenStack.
   */
  int getScreenStackCount();
  /**
   * <pre>
   *    repeated Event events = 7; TODO
   * </pre>
   *
   * <code>repeated int32 screen_stack = 6;</code>
   * @param index The index of the element to return.
   * @return The screenStack at the given index.
   */
  int getScreenStack(int index);

  /**
   * <code>map&lt;string, string&gt; attributes = 8;</code>
   */
  int getAttributesCount();
  /**
   * <code>map&lt;string, string&gt; attributes = 8;</code>
   */
  boolean containsAttributes(
      java.lang.String key);
  /**
   * Use {@link #getAttributesMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getAttributes();
  /**
   * <code>map&lt;string, string&gt; attributes = 8;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getAttributesMap();
  /**
   * <code>map&lt;string, string&gt; attributes = 8;</code>
   */

  java.lang.String getAttributesOrDefault(
      java.lang.String key,
      java.lang.String defaultValue);
  /**
   * <code>map&lt;string, string&gt; attributes = 8;</code>
   */

  java.lang.String getAttributesOrThrow(
      java.lang.String key);
}
