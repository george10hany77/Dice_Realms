





path2 = "game\gui"  #change this to name of folder

import os
import javalang
import javalang.tree
from pkg_resources import resource_string
import sys
if not sys.version_info[1] <= 11:
    raise Exception("you need python 3.11 or older")
# tokens = javalang.tokenizer.tokenize('')
# parser = javalang.parser.Parser()
# tree2 = parser.parse_expression()
path1 = str(os.path.abspath(os.getcwd())+"\\")
print(path1)

dir_list = os.listdir(path1+path2)
print(dir_list)
for name in dir_list:
    strinf = resource_string(__name__, f"{path2}\{name}")
    #print(strinf)

    tree = javalang.parse.parse(strinf)
    print(tree)
    #print(isinstance(tree.types[0],javalang.tree))
    # for x in tree.filter(javalang.tree.ClassDeclaration):
    #     print(x)
    #print(tree.types[0].name)
    #print(tree.package.name)
    array = []
    ke = {}
    k = {}

    for path, node in tree.filter(javalang.tree.MethodDeclaration):
        if node.name == "main":
            continue
        ke[node.name] = {"parameters": node.parameters, "return_type": node.return_type}
        parameters_name = []
        parameters_type = []
        parameters_dim = []
        for n in ke[node.name]["parameters"]:
            parameters_name.append(n.name)

        for n in ke[node.name]["parameters"]:
            if True or not isinstance(n.type, javalang.tree.ReferenceType):
                parameters_type.append(n.type.name)
                parameters_dim.append(len(n.type.dimensions))
        return_type = ""
        return_dim = 0
        if node.return_type == None:
            return_type = "void"
        else:
            return_type = node.return_type.name
            return_dim = len(node.return_type.dimensions)

        k[node.name] = {"parameters_name": parameters_name,
                        "parameters_type": parameters_type,
                        "parameters_dim": parameters_dim,
                        "return_type": return_type,
                        "return_dim": return_dim
                        }
    print(k)
    for kes in k.keys():
        print(k[kes])


    with open('output.md', 'a') as f:
        f.write("### `" + name + "` class\n\n")
        f.write(f"- **Package**: `{tree.package.name}`\n")
        if isinstance(tree.types[0], javalang.tree.EnumDeclaration):
            f.write(f"- **Type**: Enum Class\n")
        else:
            f.write(f"- **Type**: Class\n")
        f.write("- **Description**: This class represents ?\n\n")
        c=1
        f.write("#### Methods: \n")
        for x in k.keys():

            f.write(str(c) + ". `" + k[x]["return_type"] + ("[]" *k[x]["return_dim"]) + " " + str(x)+"`  "  +"\n")
            c+=1
            f.write("   - **Description**: ?\n")
            parameters_name =""
            for j in k[x]["parameters_name"]:
                parameters_name += "     - `"+j+"`: ?\n"
            if(not parameters_name == ""):
                f.write("   - **Parameters**:\n")
            f.write(parameters_name)
            return_name = "`"+str(k[x]["return_type"])+"`"
            return_desc = "     - ?"
            if(not return_name == "void"):
                if(k[x]["return_type"] == "boolean" ):
                    return_name = "`boolean`"

                return_dim = "return_dim"

                if(k[x]["return_dim"]>0):
                    if(k[x]["return_dim"]==1):
                        return_name = "Array of " + return_name
                    else:
                        return_name = f"{k[x][return_dim]}D"+"Array of " + return_name
                f.write(f"   - **Return Type**: {return_name}\n")
                if (k[x]["return_type"] == "boolean"):
                    f.write("      - `true` ?\n")
                    f.write("      - `false` ?.\n\n\n")
                else:
                    f.write("     - ?\n\n\n")
            else:
                f.write("\n\n")

