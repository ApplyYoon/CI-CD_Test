import sys
import json
import random 
def main():
    
    result = {
        "message": f"Hello from Python! {random.randint(1, 100)}",
        "version": sys.version
    }

    print(json.dumps(result))

if __name__ == "__main__":
    main()
