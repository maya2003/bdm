/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package bdmGenerator;

import java.util.ArrayList;
import java.util.List;

import bdmModel.BdmException;
import bdmModel.BdmField;
import bdmModel.BdmFrame;

public class BdmFrameGenerator
{
  protected BdmFrame m_bdmFrame;

  protected final String m_type;
  protected final String m_fullName;
  protected final String m_nameUpperCamel;
  protected final String m_fullNameUpper;

  protected final List<Integer> m_spare;

  protected BdmMethodGenerator m_bdmMethodGenerator;
  protected BdmMethodGenerator m_bdmSendMethodGenerator;
  protected final List<BdmFieldGenerator> bdmFieldGenerators;

  public BdmFrameGenerator(BdmProtocolGenerator bdmProtocolGenerator, BdmFrame bdmFrame)
  {
    m_bdmFrame = bdmFrame;

    BdmCaseFormat bdmCaseFormat = new BdmCaseFormat(m_bdmFrame.m_name.getValue());

    m_type = new StringBuilder(bdmProtocolGenerator.getNameUpperCamel())
      .append('_')
      .append(bdmCaseFormat.toUpperCamel()).toString();

    m_fullName = new StringBuilder(bdmProtocolGenerator.getNameUpperCamel())
      .append('_')
      .append(bdmCaseFormat.toLowerCamel()).toString();

    m_nameUpperCamel = bdmCaseFormat.toUpperCamel();

    m_fullNameUpper = new StringBuilder(bdmProtocolGenerator.getNameUpper())
      .append('_')
      .append(bdmCaseFormat.toUpper()).toString();

    m_spare = new ArrayList<Integer>();

    m_bdmMethodGenerator = new BdmMethodGenerator("void", new StringBuilder()
      .append(bdmProtocolGenerator.getNameUpperCamel())
      .append("_manage")
      .append(getNameUpperCamel())
      .append("Frame").toString());
    m_bdmMethodGenerator.addParameter("Bdm_ProtocolContext *", "context");
    if(!m_bdmFrame.fields.isEmpty())
    {
      m_bdmMethodGenerator.addParameter(getType() + " *", getName());
    }

    m_bdmSendMethodGenerator = new BdmMethodGenerator("bool", new StringBuilder()
      .append(bdmProtocolGenerator.getNameUpperCamel())
      .append("_send")
      .append(getNameUpperCamel())
      .append("Frame").toString());
    m_bdmSendMethodGenerator.addParameter("Bdm_ProtocolContext *", "context");
    for(BdmField bdmField: m_bdmFrame.fields)
    {
      m_bdmSendMethodGenerator.addParameter(bdmField.m_type.getValue(), bdmField.m_name.getValue());
    }

    bdmFieldGenerators = new ArrayList<BdmFieldGenerator>();
    for(BdmField bdmField: m_bdmFrame.fields)
    {
      bdmFieldGenerators.add(new BdmFieldGenerator(this, bdmField));
    }
  }

  /** "SampleProtocol_SampleFrame" */
  public String getType()
  {
    return m_type;
  }

  /** "sampleFrame" */
  public String getName()
  {
    return m_bdmFrame.m_name.getValue();
  }

  /** "SampleFrame" */
  public String getNameUpperCamel()
  {
    return m_nameUpperCamel;
  }

  /** "SampleProtocol_sampleFrame" */
  public String getFullName()
  {
    return m_fullName;
  }

  /** "SAMPLE_PROTOCOL_SAMPLE_FRAME" */
  public String getFullNameUpper()
  {
    return m_fullNameUpper;
  }


  public void appendFrameStructureDefinition(StringBuilder s) throws BdmException
  {
    if(bdmFieldGenerators.isEmpty())
    {
      s.append("/* (struct "); s.append(getType()); s.append(" is empty.) */\n\n");
      return;
    }

    BdmSpacer leftmargin = new BdmSpacer(2);
    BdmSpacer type       = new BdmSpacer(1);
    BdmSpacer name       = new BdmSpacer(1);
    BdmSpacer width      = new BdmSpacer();

    int currentShift;
    int spareIndex;

    /* Loop 1: compute alignments */
    currentShift = 0;
    spareIndex   = 1;

    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      int fieldShift = bdmFieldGenerator.m_bdmField.m_startByte.getValue() * 8 + bdmFieldGenerator.m_bdmField.m_startBit.getValue();

      if(fieldShift > currentShift)
      {
///// TODO u8 spare;
        type.set(2); /* u8 */
        name.set(5, spareIndex); /* "spare7" */
        width.set(0, fieldShift - currentShift);

        spareIndex++;
      }

      type.set(bdmFieldGenerator.getFieldType());
      name.set(bdmFieldGenerator.getName());
      width.set(0, bdmFieldGenerator.m_bdmField.m_size.getValue());

      currentShift = fieldShift + bdmFieldGenerator.m_bdmField.m_size.getValue();
    }

    if(currentShift % 8 != 0)
    {
      type.set(2); /* u8 */
      name.set(5, spareIndex); /* "spare7" */
      width.set(0, 8 - (currentShift % 8));
    }

    /* Loop 2: build frame structure */
    currentShift = 0;
    spareIndex   = 1;

    s.append("#pragma pack(1)\n");

    s.append("typedef struct\n");
    s.append("{\n");

    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      int fieldShift = bdmFieldGenerator.m_bdmField.m_startByte.getValue() * 8 + bdmFieldGenerator.m_bdmField.m_startBit.getValue();

      /* Insert spare bits, if needed */
      int distance = fieldShift - currentShift;
      int nbSpares = (distance + 7) / 8;

      for(int i = 0; i < nbSpares; i++)
      {
        /* bit spares */
        s.append(leftmargin.space());
        s.append("u8"); s.append(type.space(2)); /* u8 */
        s.append("spare"); s.append(spareIndex); s.append(":"); s.append(name.space(5, spareIndex)); /* spare7 */
        if(distance >= 8)
        {
          s.append(width.space(0, 8)); s.append(8);
          m_spare.add(8);
        }
        else
        {
          s.append(width.space(0, distance)); s.append(distance);
          m_spare.add(distance);
        }
        s.append(";\n");

        distance -= 8;
        spareIndex++;
      }

      s.append(leftmargin.space());
      s.append(bdmFieldGenerator.getFieldType()); s.append(type.space(bdmFieldGenerator.getFieldType()));
      s.append(bdmFieldGenerator.getName()); s.append(":"); s.append(name.space(bdmFieldGenerator.getName()));
      s.append(width.space(0, bdmFieldGenerator.m_bdmField.m_size.getValue())); s.append(bdmFieldGenerator.m_bdmField.m_size.getValue());
      s.append(";\n");

      currentShift = fieldShift + bdmFieldGenerator.m_bdmField.m_size.getValue();
    }

    if(currentShift % 8 != 0)
    {
      s.append(leftmargin.space());
      s.append("u8"); s.append(type.space(2)); /* u8 */
      s.append("spare"); s.append(spareIndex); s.append(":"); s.append(name.space(5, spareIndex)); /* "spare7" */
      s.append(width.space(0, 8 - (currentShift % 8))); s.append(8 - (currentShift % 8));
      s.append(";\n");

      m_spare.add(8 - (currentShift % 8));
    }

    s.append("} "); s.append(getType()); s.append(";\n");

    s.append("#pragma pack()\n");
    s.append("\n");
  }

  public void appendFrameIdDefinition(StringBuilder s) throws BdmException
  {
    // TODO: check max ranges / set limits
    s.append("  "); s.append(getFullNameUpper()); s.append(" = 0x"); s.append(Long.toHexString(m_bdmFrame.m_id.getValue()).toUpperCase()); s.append(",\n");
  }

  public void appendFieldEnumsDefinition(StringBuilder s)
  {
    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      if(
          (!bdmFieldGenerator.m_errorValues.m_bdmValidityAttribute.isNull())
          ||
          (!bdmFieldGenerator.m_notAvailableValues.m_bdmValidityAttribute.isNull())
          ||
          (!bdmFieldGenerator.m_validValues.m_bdmValidityAttribute.isNull())
        )
      {
        s.append("typedef enum\n" +
            "{\n");

        bdmFieldGenerator.m_errorValues.appendEnumDefinition(s);
        bdmFieldGenerator.m_notAvailableValues.appendEnumDefinition(s);
        bdmFieldGenerator.m_validValues.appendEnumDefinition(s);

        s.append("} ").append(getType()).append(bdmFieldGenerator.getType()).append("Value;\n\n");
      }
    }
  }

  public void appendFrameSize(StringBuilder s)
  {
    s.append("    case ");
    s.append(getFullNameUpper());
    s.append(":\n    {\n      *size = sizeof(");
    s.append(getType());
    s.append(");\n      break;\n    }\n\n");
  }

  public void appendFrameTypeCase(StringBuilder s)
  {
    s.append("    case "); s.append(getFullNameUpper()); s.append(":\n");
    s.append("    {\n" +
             "      /* Manage frame */\n" +
             "      ");
    if(!m_bdmFrame.fields.isEmpty())
    {
      m_bdmMethodGenerator.appendMethodCall(s, new Parameter[]{new Parameter("context", false), new Parameter("frame", true)});
    }
    else
    {
      m_bdmMethodGenerator.appendMethodCall(s, new Parameter[]{new Parameter("context", false)});
    }
    s.append("      break;\n" +
             "    }\n\n");
  }

  public void appendFrameCheckContentDeclaration(StringBuilder s)
  {
    m_bdmMethodGenerator.appendMethodDeclaration(s);
  }

  public void appendSendFrameDeclaration(StringBuilder s)
  {
    m_bdmSendMethodGenerator.appendMethodDeclaration(s);
  }

  public void appendCheckFrameContent(StringBuilder s)
  {
    m_bdmMethodGenerator.appendMethodDefinition(s);
    s.append("{\n");

    s.append("  /* Suppose the frame to be valid */\n" +
             "  bool valid = true;\n\n");

    appendCheckFieldsBounds(s);
    appendCheckSpares(s);
    appendDumpFrame(s);
    //appendCopyToDestination(s);

    s.append("}\n\n");
  }

  public void appendCheckFieldsBounds(StringBuilder s)
  {
    s.append("  /* Check values of fields */\n");

    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      bdmFieldGenerator.appendCheckFieldBounds(s);
    }
  }

  public void appendCheckSpares(StringBuilder s)
  {
    s.append("  /* Check spare values */\n");

    for(int spare = 0; spare < m_spare.size(); spare++)
    {
      int check = 0;

      for(int i = 0; i < m_spare.get(spare); i++)
      {
        check = check << 1 | 1;
      }

      s.append("  if("); s.append(this.getName()); s.append("->spare"); s.append(spare + 1); s.append(" != " + "0x"); s.append(Integer.toHexString(check).toUpperCase()); s.append(")\n" +
               "  {\n" +
               "    /* spare invalid */\n" +
               "    valid = false;\n" +
               "  }\n\n");
    }
  }

  public void appendCopyToDestination(StringBuilder s)
  {
    s.append("  /* Copy frame fields to BDM fields */\n" +
             "  if(valid)\n" +
             "  {\n");

    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      s.append("    ")
       .append(bdmFieldGenerator.m_bdmField.m_destination.getValue())
       .append(" = ")
       .append(bdmFieldGenerator.m_fullName)
       .append(";\n");
    }

    s.append("  }\n");
  }

  public void appendDumpFrame(StringBuilder s)
  {
    s.append("  /* Dump frame and frame fields */\n" +
             "  if(valid)\n" +
             "  {\n");

    s.append("    printf(\"").append(getNameUpperCamel()).append(":\\n\");\n");

    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      s.append("    printf(\"  .")
       .append(bdmFieldGenerator.getName())
       .append(": %d\\n\", ")
       .append(bdmFieldGenerator.m_fullName)
       .append(");\n");
    }

    s.append("  }\n");
  }

  public void appendSendFrame(StringBuilder s)
  {
    m_bdmSendMethodGenerator.appendMethodDefinition(s);
    s.append("{\n");
    s.append("  "); s.append(getType()); s.append(' '); s.append(getName()); s.append(";\n\n");
    for(BdmFieldGenerator bdmFieldGenerator: bdmFieldGenerators)
    {
      bdmFieldGenerator.appendFillField(s);
    }

    s.append("\n  return Bdm_protocolSendFrame(context, "); s.append(getFullNameUpper()); s.append(", &"); s.append(getName()); s.append(", sizeof(");  s.append(getName()); s.append("));\n}\n\n");
  }

}

