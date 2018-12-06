import qualified Data.List as List
import qualified Data.Map as Map

type Point = (Int, Int)

main = do
    lns <- readFile "input.txt" >>= return . lines
    let pts = [(read $ "(" ++ ln ++ ")") :: Point | ln <- lns]
    let (xs, ys) = unzip pts
    let ubound = maximum ys
    let bbound = minimum ys
    let lbound = minimum xs
    let rbound = maximum xs
    let pairs = [(x, y) | x <- [lbound..rbound], y <- [bbound..ubound]]
    print . length $ filter (\p -> sumDists pts p < 10000) pairs
    
dist :: Point -> Point -> Int
dist (x0, y0) (x1, y1) = abs (x0 - x1) + abs (y0 - y1)

sumDists :: [Point] -> Point -> Int
sumDists cs p = sum $ map (dist p) cs
