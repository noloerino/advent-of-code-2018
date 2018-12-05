import qualified Data.Char as Char

main = do
    lns <- readFile "input.txt" >>= return . lines
    let (s1, s2) = head $ filter diff [(a, b) | a <- lns, b <- lns, a > b]
    print . unzip $ filter (\(a, b) -> a == b) (zip s1 s2)

-- checks that two strings differ by one letter
diff :: (String, String) -> Bool
diff (as, bs)
    | length as == length bs = (foldl (+) 0 $ map (\(a, b) -> if Char.ord a /= Char.ord b then 1 else 0) (zip as bs)) == 1
    | otherwise = False
