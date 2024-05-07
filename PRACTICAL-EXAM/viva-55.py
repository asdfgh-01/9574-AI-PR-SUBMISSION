import random

cities = {'A': (0, 0), 'B': (5, 2), 'C': (6, 3), 'D': (3, 4), 'E': (2, 5)}
num_iterations, pop_size = 100, 50

dist = lambda c1, c2: ((c1[0] - c2[0])**2 + (c1[1] - c2[1])**2)**0.5
route_dist = lambda route: sum(dist(cities[route[i]], cities[route[i+1]]) for i in range(len(route)-1))

population = [random.sample(list(cities.keys()), len(cities)) for _ in range(pop_size)]

for _ in range(num_iterations):
    p1, p2 = random.sample(population, 2)
    start, end = sorted(random.sample(range(len(cities)), 2))
    child = p1[start:end] + [c for c in p2 if c not in p1[start:end]]
    if random.random() < 0.1:
        idx1, idx2 = random.sample(range(len(child)), 2)
        child[idx1], child[idx2] = child[idx2], child[idx1]
    population.remove(max(population, key=route_dist)); population.append(child)

best_route = min(population, key=route_dist)
print("Best route:", best_route, "\nTotal distance:", route_dist(best_route))
