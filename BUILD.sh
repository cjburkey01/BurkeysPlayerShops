cd "$(dirname "$0")"

echo "Building Burkey's Player Shops..."
rm -r target
mvn clean package
echo "Done!"