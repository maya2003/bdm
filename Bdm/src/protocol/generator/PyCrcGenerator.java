/* Copyright (c) 2013, 2014, 2015, 2016 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

package protocol.generator;

import java.util.Properties;

import org.python.util.PythonInterpreter;

//Slow?
// --> http://bytes.com/topic/python/insights/949995-three-ways-run-python-programs-java


public class PyCrcGenerator
{
  public void createCrcFiles()
  {
    /* Script directory */
    Properties properties = new Properties();
    properties.setProperty("python.path", System.getProperty("user.dir") + "/pycrc");
    PythonInterpreter.initialize(System.getProperties(), properties, new String[] {""});

    PythonInterpreter pythonInterpreter = new PythonInterpreter();

    /* Output directory */
    pythonInterpreter.exec("import os;");
    pythonInterpreter.exec("os.chdir('" + System.getProperty("user.dir") + "/../generated_sources');");

    /* Command line parameters for header file */
    pythonInterpreter.exec("import sys;");
    pythonInterpreter.exec("sys.argv.append('--model')");
    pythonInterpreter.exec("sys.argv.append('crc-16')");
    pythonInterpreter.exec("sys.argv.append('--algorithm')");
    pythonInterpreter.exec("sys.argv.append('table-driven')");
    pythonInterpreter.exec("sys.argv.append('--crc-type')");
    pythonInterpreter.exec("sys.argv.append('uint16_t')");
    pythonInterpreter.exec("sys.argv.append('--symbol-prefix')");
    pythonInterpreter.exec("sys.argv.append('Bdm_crc16_')");
    pythonInterpreter.exec("sys.argv.append('--generate')");
    pythonInterpreter.exec("sys.argv.append('h')");
    pythonInterpreter.exec("sys.argv.append('-o')");
    pythonInterpreter.exec("sys.argv.append('bdm_crc.h')");

    /* Run the script */
    pythonInterpreter.exec("import pycrc;");
    pythonInterpreter.exec("pycrc.main();");

    /* Command line parameters for implementation file */
    pythonInterpreter.exec("sys.argv.remove('--generate')");
    pythonInterpreter.exec("sys.argv.remove('h')");
    pythonInterpreter.exec("sys.argv.remove('-o')");
    pythonInterpreter.exec("sys.argv.remove('bdm_crc.h')");
    pythonInterpreter.exec("sys.argv.append('--generate')");
    pythonInterpreter.exec("sys.argv.append('c')");
    pythonInterpreter.exec("sys.argv.append('-o')");
    pythonInterpreter.exec("sys.argv.append('bdm_crc.c')");

    /* Run the script */
    pythonInterpreter.exec("import pycrc;");
    pythonInterpreter.exec("pycrc.main();");
  }

}
