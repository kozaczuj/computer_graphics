import pygame
from ball import Ball
from pygame import K_UP, K_DOWN, K_LEFT, K_RIGHT, K_SPACE, K_1, K_2, K_3, K_4, K_q, K_w, K_e

WINDOW_WIDTH = 400
WINDOW_HEIGHT = 400
config_file = "config.json"

window = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
running = True
render = True
table = None
sphere = Ball()

while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
        if event.type == pygame.KEYDOWN:
            if event.key == K_UP:
                sphere.move_light_pos_y(-1)
                render = True
            if event.key == K_DOWN:
                sphere.move_light_pos_y(1)
                render = True
            if event.key == K_LEFT:
                sphere.move_light_pos_x(-1)
                render = True
            if event.key == K_RIGHT:
                sphere.move_light_pos_x(1)
                render = True
            if event.key == K_SPACE:
                sphere.reset()
                render = True
            if event.key == K_1:
                sphere.change_material(0)
                render = True
            if event.key == K_2:
                sphere.change_material(1)
                render = True
            if event.key == K_3:
                sphere.change_material(2)
                render = True
            if event.key == K_4:
                sphere.change_material(3)
                render = True
            if event.key == K_q:
                sphere.change_light(0)
                render = True
            if event.key == K_w:
                sphere.change_light(1)
                render = True
            if event.key == K_e:
                sphere.change_light(2)
                render = True

    if render:
        pygame.draw.rect(window, (0, 0, 0), (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT))
        sphere.draw_sphere(window)
        pygame.display.update()
        render = False

