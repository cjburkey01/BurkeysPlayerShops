cd "$(dirname "$0")"

./BUILD.sh
echo "Copying to server."
cp target/*.jar run/plugins/playershops.jar
echo "Done!"

cd run
echo "Starting server..."
java -jar -Xmx2G -Xms2G -server spigot-1.12.2.jar nogui
echo "Done!"