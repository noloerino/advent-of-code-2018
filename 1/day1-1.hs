main = do
    contents <- readFile "input.txt"
    let lns = lines contents
    print $ foldl (\acc x -> acc + (readInt x :: Int)) 0 lns

readInt :: String -> Int
readInt ('-':xs) = read ('-':xs) :: Int
readInt ('+':xs) = read xs :: Int
readInt "" = 0
