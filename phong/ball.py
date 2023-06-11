import math
import random
import pygame
from pygame import Surface


class Ball:
    def __init__(self, width=400, height=400):
        self.__default()

        self.width = width
        self.height = height

        self.camera_pos = [0, 0, 200]
        self.light_col = [0.79, 0.89, 1]
        self.light_pos = [200, 400, 500]

        self.radius = min(width, height) / 3
        self.center = [width/2, height/2, 0]

    def draw_sphere(self, window: Surface):
        for x in range(-self.width, self.width):
            for y in range(-self.height, self.height):
                distance = math.sqrt(x ** 2 + y ** 2)
                if distance < self.radius:
                    z = math.sqrt(self.radius ** 2 - distance ** 2)

                    point = [x + self.center[0], y + self.center[1], z]

                    # N
                    normal_vec = self.__vec_normalize(
                        self.__vec_from_pnt_to_pnt(self.center, point)
                    )

                    # V
                    viewer_vec = self.__vec_normalize(
                        self.__vec_from_pnt_to_pnt(point, self.camera_pos)
                    )

                    # L
                    light_vec = self.__vec_normalize(
                        self.__vec_from_pnt_to_pnt(point, self.light_pos)
                    )

                    # R
                    reflection_vec = self.__vec_normalize(
                        self.__vec_reflection(light_vec, normal_vec)
                    )

                    ambient_rgb = self.__ambient()
                    diffuse_rgb = self.__diffuse(normal_vec, light_vec)
                    specular_rgb = self.__specular(reflection_vec, viewer_vec)

                    illumination = self.__vec_sum([ambient_rgb, diffuse_rgb, specular_rgb])

                    color = (
                        int(self.__normalize(illumination[0]) * 255),
                        int(self.__normalize(illumination[1]) * 255),
                        int(self.__normalize(illumination[2]) * 255)
                    )

                    pygame.draw.line(window, color, (point[0], point[1]), (point[0], point[1]))

    def __ambient(self) -> [float, float, float]:
        result = []
        for i in range(3):
            result.append(self.k_a[i]*self.light_col[i])
        return result

    def __diffuse(self, normal_vec:list, light_vec: list) -> [float, float, float]:
        result = []
        diffuse = max(0.0, self.__vec_dot_product(light_vec, normal_vec))
        for i in range(3):
            result.append(self.k_d[i] * diffuse * self.light_col[i])
        return result

    def __specular(self, reflection_vec: list, viewer_vec: list) -> [float, float, float]:
        result = []
        for i in range(3):
            result.append(
                math.pow(max(0.0, self.__vec_dot_product(reflection_vec, viewer_vec)),
                         self.spec_pow) * self.k_s[i] * self.light_col[i])
        return result

    @staticmethod
    def __normalize(value: float) -> float:
        if value > 1:
            return 1
        if value < 0:
            return 0
        return value

    @staticmethod
    def __vec_from_pnt_to_pnt(vector_start: list, vector_end: list) -> [float, float, float]:
        result = []
        if len(vector_start) != len(vector_end) != 3:
            raise ValueError
        for i in range(3):
            result.append(vector_end[i] - vector_start[i])
        return result

    @staticmethod
    def __vec_normalize(vector: list) -> [float, float, float]:
        result = []
        if len(vector) != 3:
            raise ValueError
        vector_magnitude = math.sqrt(vector[0] ** 2 + vector[1] ** 2 + vector[2] ** 2)
        for i in range(3):
            result.append(vector[i] / vector_magnitude)
        return result

    @staticmethod
    def __vec_dot_product(vector_1: list, vector_2: list) -> [float, float, float]:
        if len(vector_1) != len(vector_2):
            raise ValueError

        result = 0
        for i in range(len(vector_1)):
            result += vector_1[i] * vector_2[i]

        return result

    @staticmethod
    def __vec_sum(array_of_vectors: list) -> [float, float, float]:
        result = []
        for i in range(3):
            result.append(0.0)
            for j in range(len(array_of_vectors)):
                result[i] += array_of_vectors[j][i]
        return result

    def __vec_reflection(self, vector: list, normal: list) -> [float, float, float]:
        result = []
        r = self.__vec_dot_product(vector, normal)
        for i in range(3):
            result.append(2 * r * normal[i] - vector[i])
        return result

    def __default(self):
        self.k_a = [0.49, 0.473, 0.422]
        self.k_d = [0.90, 0.97, 0.98]
        self.k_s = [0.956, 0.937, 0.986]
        self.spec_pow = 3

    def __bronze(self):
        self.k_a = [0.33, 0.22, 0.027]
        self.k_d = [0.78, 0.57, 0.11]
        self.k_s = [0.99, 0.94, 0.81]
        self.spec_pow = 27.9

    def __chrome(self):
        self.k_a = [0.25, 0.25, 0.25]
        self.k_d = [0.4, 0.4, 0.4]
        self.k_s = [0.774597, 0.774597, 0.774597]
        self.spec_pow = 76.8

    def __obsidian(self):
        self.k_a = [0.05375, 0.05, 0.06625]
        self.k_d = [0.18275, 0.17, 0.22525]
        self.k_s = [0.332741, 0.328634, 0.346435]
        self.spec_pow = 38.4

    def move_light_pos_x(self, direction):
        self.light_pos[0] += direction * 50

    def move_light_pos_y(self, direction):
        self.light_pos[1] += direction * 50

    def reset(self):
        self.light_pos = [200, 400, 500]
        self.light_col = [0.79, 0.89, 1]

    def change_material(self, number: int):
        if number == 0:
            self.__default()
        elif number == 1:
            self.__bronze()
        elif number == 2:
            self.__chrome()
        elif number == 3:
            self.__obsidian()

    def change_light(self, number: int):
        if number == 0:
            self.light_col = [1, 0.58, 0.16]  # candle
        elif number == 1:
            self.light_col = [0.79, 0.89, 1]  # sky
        elif number == 2:
            self.light_col = [1, 1, 1]  # direct sunlight
