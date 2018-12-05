import qualified Data.Map as Map

main = do
    lns <- readFile "input.txt" >>= return . lines
    let (a, b) = foldl updateCounts (0, 0) lns
    print $ a * b

type Counts = (Int, Int)
updateCounts :: Counts -> String -> Counts
updateCounts (twos, threes) s = (if 2 `elem` cs then 1 + twos else twos,
    if 3 `elem` cs then 1 + threes else threes)
    where cs = Map.elems $ charCounts s


charCounts :: String -> Map.Map Char Int
charCounts s = Map.fromListWith (+) $ zip s (cycle [1])

