cd "$(dirname "$0")"

echo "Cleaning up previous builds..."
rm -r target
echo "Done!"

echo "Building Burkey's Player Shops..."
mvn clean package
echo "Done!"