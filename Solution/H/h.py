import sys
from collections import deque

def solve():
    input_data = sys.stdin.read().split()
    if not input_data:
        return
    
    N = int(input_data[0])
    M = int(input_data[1])
    Q = int(input_data[2])
    
    adj = [[] for _ in range(N + 1)]
    idx = 3
    for _ in range(M):
        u = int(input_data[idx])
        v = int(input_data[idx+1])
        adj[u].append(v)
        adj[v].append(u)
        idx += 2
        
    in_S = [False] * (N + 1)
    color = [-1] * (N + 1)
    
    for _ in range(Q):
        k = int(input_data[idx])
        idx += 1
        
        queries = []
        for _ in range(k):
            node = int(input_data[idx])
            queries.append(node)
            in_S[node] = True
            idx += 1
            
        is_bipartite = True
        
        for start_node in queries:
            if color[start_node] == -1:
                color[start_node] = 0
                queue = deque([start_node])
                
                while queue:
                    u = queue.popleft()
                    c = color[u]
                    
                    for v in adj[u]:
                        if in_S[v]:
                            if color[v] == -1:
                                color[v] = c ^ 1
                                queue.append(v)
                            elif color[v] == c:
                                is_bipartite = False
                                break
                                
                    if not is_bipartite:
                        break
                        
            if not is_bipartite:
                break
                
        if is_bipartite:
            print("YES")
        else:
            print("NO")
            
        for node in queries:
            in_S[node] = False
            color[node] = -1

if __name__ == '__main__':
    solve()