import numpy as np
from PIL import Image
import matplotlib.pyplot as plt
import matplotlib.colors as mcolors
import matplotlib.patches as mpatches
from mpl_toolkits.mplot3d import Axes3D

def positions():
    #The positions of the tower are fixed at these positions
    fixed = [-9.1, -6.4, -4.75, -3.2, -1.74, -0.1, 1.4, 3.1, 4.5, 6.1, 9.1]
    items = []

    #For the left towers
    for i in range(len(fixed)):
        items.append((-9.1, fixed[i]))

    #For the top towers; exclude the first value because the previous for-loop plotted it
    for i in range(1, len(fixed)):
        items.append((fixed[i], 9.1))

    #Reverse the values because for the right and bottom we plot from the top down or right to left, respectively
    fixed = fixed[::-1]

    #For the right towers
    for i in range(1, len(fixed)):
        items.append((9.1, fixed[i]))

    #For the bottom towers. Exclude the first value (see above why) and last value (because the first for-loop plotted it)
    for i in range(1, len(fixed) - 1):
        items.append((fixed[i], -9.1))

    return items

def main():
    file = open("../data/tree_paths.txt")
    lines = file.readlines()
    file.close()

    p, h, total = list(map(int, lines[0].split()))
    lines = lines[1:]

    board = np.zeros(40).tolist()

    #Sum the values for each roll
    for line in lines:
        rolls = list(map(int, line.strip().split("$")))

        index = 0
        for roll in rolls:
            index = ((index + roll) % 40)

            #Index=30 implies we have to go to jail (index 10)
            if index == 30:
                index = 10

            #Increase the value at that index to indicate we have landed there
            board[index] += 1

    print(board)

    #Normalize board values to speed up plotting
    max_value = max(board)
    board_normalized = [value / max_value for value in board]
    
    #Add up respective values from board
    labels = {
        "Brown": sum([board_normalized[1], board_normalized[3]]),
        "Cyan": sum([board_normalized[6], board_normalized[8], board_normalized[9]]),
        "Pink": sum([board_normalized[11], board_normalized[13], board_normalized[14]]),
        "Orange": sum([board_normalized[16], board_normalized[18], board_normalized[19]]),
        "Red": sum([board_normalized[21], board_normalized[23], board_normalized[24]]),
        "Yellow": sum([board_normalized[26], board_normalized[27], board_normalized[29]]),
        "Green": sum([board_normalized[31], board_normalized[32], board_normalized[34]]),
        "Dark Blue": sum([board_normalized[37], board_normalized[39]]),
        "Stations": sum([board_normalized[5], board_normalized[15], board_normalized[25], board_normalized[35]]),
        "Utilities": sum([board_normalized[12], board_normalized[28]])
    }

    #Collect indices for specific colours for labelling
    indices = {
        "Brown": [1, 3],
        "Cyan": [6, 8, 9],
        "Pink": [11, 13, 14],
        "Orange": [16, 18, 19],
        "Red": [21, 23, 24],
        "Yellow": [26, 27, 29],
        "Green": [31, 32, 34],
        "Dark Blue": [37, 39],
        "Stations": [5, 15, 25, 35],
        "Utilities": [12, 28]
    }

    #Colours for labelling
    colors = {
        "Brown": "saddlebrown",
        "Cyan": "cyan",
        "Pink": "hotpink",
        "Orange": "orange",
        "Red": "red",
        "Yellow": "yellow",
        "Green": "green",
        "Dark Blue": "darkblue",
        "Stations": "black",
        "Utilities": "purple",
        "Default": "grey"
    }

    board_img = Image.open("../assets/Board_original.png")
    board_texture = np.array(board_img)

    #Have to flip the image vertically; somehow it gets loaded upside down
    board_texture = np.flipud(board_texture)

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    #Display board
    x, y = np.meshgrid(np.linspace(-10, 10, board_texture.shape[1]), np.linspace(-10, 10, board_texture.shape[0]))
    ax.plot_surface(x, y, np.zeros_like(x), rstride=5, cstride=5, facecolors=board_texture / 255., shade=False)
    
    #Set tower properties, get tower positions and plot
    tower_height = 50  
    tower_width = 0.8  

    tower_positions = positions()

    for i, (x_pos, y_pos) in enumerate(tower_positions):
        height = board_normalized[i] * tower_height

        # Determine color based on indices
        color = colors["Default"]
        for key, value in indices.items():
            if i in value:
                color = colors[key]
                break

        ax.bar3d(x_pos, y_pos, 0, tower_width, tower_width, height, color=color, shade=True)


    ax.set_xlim([-11, 11])
    ax.set_ylim([-11, 11])
    ax.set_zlim([0, tower_height])
    ax.axis('off')

    #Add legend with data
    sorted_labels = sorted(labels.items(), key=lambda item: item[1], reverse=True)
    legend_handles = [mpatches.Patch(color=colors[key], label=f"{key}: {value:.2f}") for key, value in sorted_labels]
    legend = plt.legend(handles=legend_handles, loc='upper left', bbox_to_anchor=(1.05, 1), borderaxespad=0., title="Relative Property Values")

    plt.title(f"Roll distribution after {h} rolls with {p} of 11 roll values")

    # Add rotation and zoom interaction: credit to ChatGPT for this
    def on_move(event):
        if event.button == 1:
            ax.view_init(elev=ax.elev + (event.y - on_move.y) / 10,
                         azim=ax.azim + (event.x - on_move.x) / 10)
        elif event.button == 3:
            ax.dist = ax.dist - (event.y - on_move.y) / 20
        fig.canvas.draw_idle()
        on_move.x, on_move.y = event.x, event.y

    fig.canvas.mpl_connect('motion_notify_event', on_move)
    fig.canvas.mpl_connect('button_press_event', lambda event: setattr(on_move, 'x', event.x) or setattr(on_move, 'y', event.y))

    plt.show()

if __name__ == "__main__":
    main()
