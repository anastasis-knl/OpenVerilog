APPNAME=OpenVerilog
SRC_DIR=src/main/java
SRC=$(shell find $(SRC_DIR) -name "*.java")
OUTDIR=build
MAIN_CLASS=org.verilog_compiler.Start

all: $(OUTDIR)/$(APPNAME).jar

$(OUTDIR)/$(APPNAME).jar: $(SRC)
	mkdir -p $(OUTDIR)
	javac -d $(OUTDIR) $(SRC)
	jar cfe $(OUTDIR)/$(APPNAME).jar $(MAIN_CLASS) -C $(OUTDIR) .

install:
	mkdir -p /usr/local/bin
	cp $(OUTDIR)/$(APPNAME).jar /usr/local/bin/$(APPNAME).jar
	echo '#!/bin/sh' > /usr/local/bin/$(APPNAME)
	echo 'java -jar /usr/local/bin/$(APPNAME).jar "$$@"' >> /usr/local/bin/$(APPNAME)
	chmod +x /usr/local/bin/$(APPNAME)

clean:
	rm -rf $(OUTDIR)
	rm -f /usr/local/bin/$(APPNAME) /usr/local/bin/$(APPNAME).jar
