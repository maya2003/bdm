# Copyright (c) 2013, 2014, 2015 Olivier TARTROU
# See the file COPYING for copying permission.
#
# https://sourceforge.net/projects/bdm-generator/

all: bdm_serial

main.o: main.c
	gcc -std=c11 -Wall -Wextra -c main.c -o main.o

bdm_serial.o: bdm_serial.c
	gcc -std=c11 -Wall -Wextra -c bdm_serial.c -o bdm_serial.o

bdm_serial: main.o bdm_serial.o
	gcc main.o bdm_serial.o -o bdm_serial

clean:
	rm -f *~ *.o bdm_serial

