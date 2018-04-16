import sys


def main(args):
	print('Hello WOrld!')
	print(args)
	

if __name__ == "__main__":
    # execute only if run as a script
    main(sys.argv[1:])