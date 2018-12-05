import qualified Data.Set as Set

main = do
    contents <- readFile "input.txt"
    let lns = lines contents
    print $ calc (cycle lns) 0 (Set.singleton 0)

calc :: [String] -> Int -> Set.Set Int -> Maybe Int
calc [] _ seen = Nothing
calc (s:ss) prev seen = if n `Set.member` seen then Just n else calc ss n (Set.insert n seen)
    where n = readInt s + prev

readInt :: String -> Int
readInt ('-':xs) = read ('-':xs) :: Int
readInt ('+':xs) = read xs :: Int
readInt "" = 0

