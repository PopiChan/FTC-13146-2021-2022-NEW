import pygame

pygame.init()


class Node():
    def __init__(self, x, y, parent, color, move, end):
        if end != None:
            self.H = abs(end[0]/25 - x/25) + abs(end[1]/25 - y/25)
        else:
            self.H = 0
        self.G = 1
        self.F = self.G + self.H
        self.x = x
        self.y = y
        self.child = []
        self.color = color
        self.move = move

        self.parent = parent

    def AddChild(self, end, current_node, nodes):
        for node in nodes[0]:
            if pygame.Rect(nodes[Closed][current_node].x + tile, nodes[Closed][current_node].y, tile, tile).colliderect(pygame.Rect(node.x, node.y, tile, tile)):
                node.color = (0, 255-self.F, 255)
                node.parent = nodes[Closed][current_node]
                nodes[Closed][current_node].child = node
                nodes[1].append(node)
            elif pygame.Rect(nodes[Closed][current_node].x - tile, nodes[Closed][current_node].y, tile, tile).colliderect(pygame.Rect(node.x, node.y, tile, tile)):
                node.color = (0, 255-self.F, 255)
                node.parent = nodes[Closed][current_node]
                nodes[Closed][current_node].child = node
                nodes[1].append(node)

            elif pygame.Rect(nodes[Closed][current_node].x, nodes[Closed][current_node].y - tile, tile, tile).colliderect(pygame.Rect(node.x, node.y, tile, tile)):
                node.color = (0, 255-self.F, 255)
                node.parent = nodes[Closed][current_node]
                nodes[Closed][current_node].child = node
                nodes[1].append(node)
            elif pygame.Rect(nodes[Closed][current_node].x, nodes[Closed][current_node].y + tile, tile, tile).colliderect(pygame.Rect(node.x, node.y, tile, tile)):
                node.color = (0, 255-self.F, 255)
                node.parent = nodes[Closed][current_node]
                nodes[Closed][current_node].child = node
                nodes[1].append(node)

        for i in range(0, len(nodes[1])-1):
            if nodes[1][0] in nodes[0]:
                nodes[0].remove(nodes[0][nodes[1][0]])



    def Draw(self, font):
        pygame.draw.rect(screen, (0, 0, 0), (self.x, self.y, tile, tile))
        pygame.draw.rect(screen, self.color, (self.x+1, self.y+1, tile-2, tile-2))



        label = font.render((str(self.H)), True, (0, 0, 0))
        screen.blit(label, (self.x+3, self.y+5))
        label = font.render((str(self.F)), True, (0, 0, 0))
        screen.blit(label, (self.x+3, self.y+int(tile/2)))

    def Source(self):
        return self.parent, self



Open = 0
Closed = 1

nodes = [[], []]
current_node = 0
used_nodes = []

path = []

W = 1000
H = 950

tile = 25

screen = pygame.display.set_mode((W, H))
pygame.display.set_caption("A*")

clock = pygame.time.Clock()

End = Node(500, 650, None, (255, 0, 0), False, None)
Start = Node(300, 300, None, (0, 255, 0), False, [End.x, End.y])


for i in range(0, int(H/tile)):
    for j in range(0, int(W/tile)):
        nodes[Open].append(Node(j*tile, i*tile, None, (255, 255, 255), False, [End.x, End.y]))

for node in nodes[Open]:
    if pygame.Rect(Start.x, Start.y, tile, tile).colliderect(pygame.Rect(node.x, node.y, tile, tile)):
        print(nodes[Open][nodes[Open].index(node)+1].x, nodes[Open][nodes[Open].index(node)+1].y)
        nodes[Closed].insert(nodes[Open].index(node), node)
        node.color = (0, 255, 255)
        nodes[Open].remove(node)


font = pygame.font.Font(None, int(tile/2))

A = W*tile
B = None
Break = False

Draw = False

while len(path) == 0:
    clock.tick(120)
    pygame.display.flip()
    screen.fill((0, 0, 0))
    for e in pygame.event.get():
        if e.type == pygame.QUIT:
            pygame.quit()

        elif e.type == pygame.KEYDOWN:

            if e.key == pygame.K_SPACE and Draw == False:
                Draw = True
            elif e.key == pygame.K_SPACE and Draw == True:
                Draw = False





            elif e.key == pygame.K_c:
                for i in nodes[Closed]:
                    if i.child == []:
                        pygame.draw.rect(screen, (255, 255, 255), (i.x, i.y, tile, tile))
                        clock.tick(10)
                        pygame.display.flip()
                    else:
                        pygame.draw.rect(screen, (255, 0, 0), (i.x, i.y, tile, tile))


        elif e.type == pygame.MOUSEBUTTONDOWN:
            for i in nodes:
                for node in i:
                    x, y = pygame.mouse.get_pos()
                    if pygame.Rect(x, y, 1, 1).colliderect(pygame.Rect(node.x, node.y, tile, tile)):
                        B = [node, nodes.index(i)]
                        Break = True
                    elif Break == False:
                        B = [x, y]


            if B != None:
                if Break == False:
                    nodes[Open].append(Node(int(x/tile)*tile, int(y/tile)*tile, None, (255, 255, 255), False, [End.x, End.y]))
                else:
                    nodes[B[1]].remove(B[0])

    if Draw == True:
        A = W*H*tile
        used_nodes.append(current_node)
        nodes[Open][0].AddChild(End, current_node, nodes)
        for node in nodes[Closed]:
            if node.F < A and nodes[Closed].index(node) != 0 and nodes[Closed].index(node) not in used_nodes:
                A = node.F
                current_node = nodes[Closed].index(node)
            elif current_node in used_nodes:
                current_node += 1





    for node in nodes[Closed]:
        if pygame.Rect(End.x, End.y, tile, tile).colliderect(node.x, node.y, tile, tile):
            parent, self = nodes[Closed][len(nodes[Closed])-1].Source()
            path.append(parent)
            nodes[Open].append(self)
            nodes[Closed].remove(self)
            break
    try:
        while [path[len(path)-1].x, path[len(path)-1].y] != [Start.x, Start.y]:
            parent, self = nodes[Closed][len(nodes[Closed])-1].Source()
            if parent not in path:
                path.append(parent)
            nodes[Open].append(self)
            nodes[Closed].remove(self)
    except: IndexError


    if Break == True:
        Break = False



    for i in nodes[Open]:
        i.Draw(font)

    for i in nodes[Closed]:
        i.Draw(font)

    Start.Draw(font)
    End.Draw(font)

while True:
    clock.tick(10)
    pygame.display.flip()
    screen.fill((0, 0, 0))
    for e in pygame.event.get():
        if e.type == pygame.QUIT:
            pygame.quit()


    if len(path) > 0:
        End.x = path[0].x
        End.y = path[0].y
        path.remove(path[0])





    for i in nodes[Open]:
        i.Draw(font)

    for i in nodes[Closed]:
        i.Draw(font)

    Start.Draw(font)
    End.Draw(font)