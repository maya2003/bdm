/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.
*/

package bdmGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BdmMethodGenerator
{
  protected String m_returnType;
  protected String m_name;
  
  protected List<BdmMethodParameter> m_parameters;
  
  public BdmMethodGenerator(String returnType, String name)
  {
    m_returnType = returnType;
    m_name       = name;

    m_parameters = new ArrayList<BdmMethodParameter>();
  }

  public void addParameter(String type, String name)
  {
    m_parameters.add(new BdmMethodParameter(type, name));
  }

  public void appendMethodDeclaration(StringBuilder s)
  {
    boolean first = true;

    s.append("/* ");
    s.append(m_name);
    s.append(" */\n");

    s.append(m_returnType);
    s.append(' ');
    s.append(m_name);
    s.append('(');
    for(BdmMethodParameter parameter: m_parameters)
    {
      if(!first)
      {
        s.append(", ");
      }

      s.append(parameter.getType());
      
      if(!parameter.isPointer())
      {
        s.append(' ');
      }

      s.append(parameter.getName());

      first = false;
    }
    s.append(");\n\n");
  }

  public void appendMethodDefinition(StringBuilder s)
  {
    boolean first = true;

    s.append("/* ");
    s.append(m_name);
    s.append(" */\n");

    s.append(m_returnType);
    s.append(' ');
    s.append(m_name);
    s.append('(');
    for(BdmMethodParameter parameter: m_parameters)
    {
      if(!first)
      {
        s.append(", ");
      }

      s.append(parameter.getType());

      if(!parameter.isPointer())
      {
        s.append(' ');
      }

      s.append(parameter.getName());

      first = false;
    }
    s.append(")\n");
  }

  public void appendMethodCall(StringBuilder s, String[] parameters, boolean cast)
  {
    boolean first = true;

    s.append(m_name);
    s.append('(');
    
    Iterator<BdmMethodParameter> it = m_parameters.iterator();
    
    for(String parameter: parameters)
    {
      if(!first)
      {
        s.append(", ");
      }

      if(cast)
      {
        s.append("(");
        s.append(it.next().m_type);
        s.append(")");
      }

      s.append(parameter);

      first = false;
    }

    s.append(");\n");
  }

}
