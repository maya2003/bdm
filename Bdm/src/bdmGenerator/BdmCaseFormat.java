/* Copyright (c) 2013, 2014 Olivier TARTROU
   See the file COPYING for copying permission.

   https://sourceforge.net/projects/bdm-generator/
*/

package bdmGenerator;

/** Based on Google Guava
 * https://code.google.com/p/guava-libraries/
 */
import com.google.common.base.CaseFormat;

public class BdmCaseFormat
{
  protected final String m_lowerCamel;
  protected final String m_upperCamel;
  protected final String m_upper;

  public BdmCaseFormat(String lowerCamelName)
  {
    m_lowerCamel = lowerCamelName;
    m_upperCamel = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,      lowerCamelName);
    m_upper      = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, lowerCamelName);
  }

  public String toLowerCamel()
  {
    return m_lowerCamel;
  }

  public String toUpperCamel()
  {
    return m_upperCamel;
  }

  public String toUpper()
  {
    return m_upper;
  }

}
