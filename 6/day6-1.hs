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
    let a = Map.assocs $ foldl (\m p -> updateDistMap p pts m) Map.empty [(x, y) | x <- [lbound..rbound], y <- [bbound..ubound]]
    print . head . reverse $ List.sortOn snd a
    

type DistMap = Map.Map Point Int

updateDistMap :: Point -> [Point] -> DistMap -> DistMap
updateDistMap p cs m = let sorted = List.sortOn (dist p) cs in
    if dist p (sorted !! 0) /= dist p (sorted !! 1)
        then Map.insert (head sorted) ((Map.findWithDefault 0 (head sorted) m) + 1) m
        else m

dist :: Point -> Point -> Int
dist (x0, y0) (x1, y1) = abs (x0 - x1) + abs (y0 - y1)
