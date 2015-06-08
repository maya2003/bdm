# Copyright (c) 2013, 2014, 2015 Olivier TARTROU
# See the file COPYING for copying permission.
#
# https://sourceforge.net/projects/bdm-generator/

bdm: main.o bdm_linux_serial_config.o bdm_linux_serial.o bdm_linux_receive_thread.o bdm_transparency.o bdm_protocol.o bdm_dump.o bdm_crc.o bdm_frames.o size.o
	gcc main.o bdm_linux_serial_config.o bdm_linux_serial.o bdm_linux_receive_thread.o bdm_transparency.o bdm_protocol.o bdm_dump.o bdm_crc.o bdm_frames.o size.o -o bdm -lpthread

main.o: main.c bdm.h
	gcc -std=c11 -Wall -Wextra -c main.c -o main.o

bdm_linux_serial_config.o: bdm_linux_serial_config.c bdm.h
	gcc -std=c11 -Wall -Wextra -c bdm_linux_serial_config.c -o bdm_linux_serial_config.o

bdm_linux_serial.o: bdm_linux_serial.c bdm.h
	gcc -std=c11 -Wall -Wextra -c bdm_linux_serial.c -o bdm_linux_serial.o

bdm_linux_receive_thread.o: bdm_linux_receive_thread.c bdm.h
	gcc -std=c11 -Wall -Wextra -c bdm_linux_receive_thread.c -o bdm_linux_receive_thread.o

bdm_transparency.o: bdm_transparency.c bdm.h
	gcc -std=c11 -Wall -Wextra -c bdm_transparency.c -o bdm_transparency.o

bdm_protocol.o: bdm_protocol.c bdm.h bdm_crc.h bdm_frames.h
	gcc -std=c11 -Wall -Wextra -c bdm_protocol.c -o bdm_protocol.o

bdm_dump.o: bdm_dump.c bdm.h
	gcc -std=c11 -Wall -Wextra -c bdm_dump.c -o bdm_dump.o

bdm_crc.o: bdm_crc.c bdm_crc.h
	gcc -std=c11 -Wall -Wextra -c bdm_crc.c -o bdm_crc.o

bdm_frames.o: bdm_frames.c bdm_frames.h
	gcc -std=c11 -Wall -Wextra -c bdm_frames.c -o bdm_frames.o

size.o: size.c bdm.h
	gcc -std=c11 -Wall -Wextra -c size.c -o size.o

clean:
	rm -f *~ *.o bdm

