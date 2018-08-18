package udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.serde.Constants;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.Text;

public class Concat_Ws extends GenericUDF {
  private ObjectInspector[] argumentOIs;

  @Override
  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    if (arguments.length < 2) {
      throw new UDFArgumentLengthException("The function CONCAT_WSneeds at least two arguments.");
    }

    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].getTypeName() != Constants.STRING_TYPE_NAME
          && arguments[i].getTypeName() != Constants.VOID_TYPE_NAME) {
        throw new UDFArgumentTypeException(i, "Argument " + (i + 1)
            + " of function CONCAT_WS must be \"" + Constants.STRING_TYPE_NAME
            + "\", but \"" + arguments[i].getTypeName() + "\" was found.");
      }
    }

    argumentOIs = arguments;
    return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
  }

  private final Text resultText = new Text();

  @Override
  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    if (arguments[0].get() == null) {
      return null;
    }
    String separator = ((StringObjectInspector) argumentOIs[0])
        .getPrimitiveJavaObject(arguments[0].get());

    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (int i = 1; i < arguments.length; i++) {
      if (arguments[i].get() != null) {
        if (first) {
          first = false;
        } else {
          sb.append(separator);
        }
        sb.append(((StringObjectInspector) argumentOIs[i])
            .getPrimitiveJavaObject(arguments[i].get()));
      }
    }

    resultText.set(sb.toString());
    return resultText;
  }

  @Override
  public String getDisplayString(String[] children) {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();
    sb.append("concat_ws(");
    for (int i = 0; i < children.length - 1; i++) {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[children.length - 1]).append(")");
    return sb.toString();
  }
}

