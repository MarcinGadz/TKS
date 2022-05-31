#!/bin/bash
for D in *; do
 if [ -d "${D}" ]; then
	echo "Doing: ${D}";
 	 cd "${D}";
	/bin/bash ./build-image.sh;
	cd ..;
  fi
done
