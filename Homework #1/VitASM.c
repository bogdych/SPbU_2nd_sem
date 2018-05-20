#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Stack {
    int *bottom;
    int *top;
} Stack;

typedef struct Label {
    char *name;
    int ptr;
} Label;

typedef struct Instruction {
    char *cmd;
    char *arg;
} Instruction;

Label **labels;
int labelPtr;

void push(Stack *stack, int value) {
    stack->top++;
    *stack->top = value;
}

int pop(Stack *stack) {
    if (stack->top == stack->bottom) {
        printf("Stack smash detected, aborting!\n");
        exit(0);
    }
    int res = *stack->top;
    stack->top--;
    return res;
}

int peek(struct Stack *stack) {
    if (stack->top == stack->bottom) {
        printf("Stack smash detected, aborting\n");
        exit(0);
    }
    return *stack->top;
}

int pw(int x, size_t y) {
    int res = 1;
    for (size_t i = 1; i <= y; i++) {
        res = res * x;
    }
    return res;
}

int strToInt(char *str) {
    int n = 0;
    if (str[0] != '-') {
        for (int i = 0; str[i] != '\0'; i++) {
            n = n * 10 + (str[i] - '0');
        }
        return n;
    }
    else {
        for (int  i = 1; str[i] != '\0'; i++) {
            n = n * 10 + (str[i] - '0');
        }
        return -n;
    }
}

int searchForLabel(char *str) {
    for (size_t i = 0; i < labelPtr; i++) {
        if (strcmp(labels[i]->name, str) == 0) {
            return labels[i]->ptr;
        }
    }
    return -1;
}

char * getStr(FILE *file, int *ch) {
    char *str;
	size_t size = 16;
	size_t len = 0;
	str = realloc(NULL, sizeof(char) * size);
	if (!str)
		return str;
	while (EOF != (*ch = fgetc(file)) && *ch != '\n') {
		str[len++] = *ch;
		if (len == size) {
			str = realloc(str, sizeof(char) * (size += 16));
			if (!str)
				return str;
		}
	}
	str[len++] = '\0';

	return realloc(str, sizeof(char) * len);
}

char * parseStrForLabels(char *str, int currRow) {
    int isBegins = 0;
    char *cmdPtr = str;
    int isLabel = 0;
    for (int i = 0; i <= strlen(str); i++) {
        if (str[i] != ' ') {
            if (!isBegins) {
                cmdPtr = str + i;
                isBegins = 1;
            }
            if (str[i] == ':' && !isLabel) {
                labels[labelPtr] = malloc(sizeof(Label));
                if (!labels[labelPtr]) {
                    printf("Couldn't allocate memory\n");
                    exit(1);
                }
                labels[labelPtr]->name = malloc(sizeof(char) * (str + i + 1 - cmdPtr));
                if (!labels[labelPtr]->name) {
                    printf("Couldn't allocate memory\n");
                    exit(1);
                }
                for (int j = 0; cmdPtr + j < str + i; j++) {
                    labels[labelPtr]->name[j] = cmdPtr[j];
                }
                labels[labelPtr]->name[str + i - cmdPtr] = '\0';
                labels[labelPtr]->ptr = currRow;
                labelPtr++;
                isBegins = 0;
                isLabel = 1;
            }
            if (str[i] == ';') {
                str[i] = '\0';
                break;
            }
        }
    }

    return cmdPtr;
}

Instruction * parseStrForCmd(char *str, int currRow) {
    Instruction *inst = NULL;
    char *ptr = str;
    int isCmd = 0;
    int isBegins = 0;
    int withArg = 0;
    char *text = NULL;
    if (str[0] == '\0') {
        return NULL;
    }
    for (int i = 0; i <= strlen(str); i++) {
        if (!(str[i] == ' ' || str[i] == '\0')) {
            if (!isBegins) {
                ptr = str + i;
                isBegins = 1;
            }
        }
        else {
            if (isBegins) {
                isBegins = 0;
                text = malloc(sizeof(char) * (str + i + 1 - ptr));
                if (!text) {
                    printf("Couldn't allocate memory\n");
                    exit(1);
                }
                for (int j = 0; ptr + j < str + i; j++) {
                    text[j] = ptr[j];
                }
                text[str + i - ptr] = '\0';
                if (!isCmd) {
                    if (!(strcmp(text, "add") && strcmp(text, "sub") && strcmp(text, "cmp") && strcmp(text, "ret"))) {
                        inst = malloc(sizeof(Instruction));
                        if (!inst) {
                            printf("Couldn't allocate memory\n");
                            exit(1);
                        }
                        inst->cmd = text;
                        inst->arg = NULL;
                        break;
                    } else if (!(strcmp(text, "ld") &&
                                 strcmp(text, "st") && strcmp(text, "ldc") &&
                                 strcmp(text, "jmp") && strcmp(text, "br"))) {
                        inst = malloc(sizeof(Instruction));
                        if (!inst) {
                            printf("Couldn't allocate memory\n");
                            exit(1);
                        }
                        inst->cmd = text;
                        inst->arg = NULL;
                        withArg = 1;
                        isCmd = 1;
                    } else {
                        printf("Syntax error. Row %d\n", currRow);
                        exit(0);
                    }
                } else {
                    inst->arg = text;
                    withArg = 1;
                    break;
                }
            }
        }
    }
    if (inst && !inst->arg && withArg) {
        printf("Syntax error. Expected an argument in row %d\n", currRow);
        exit(0);
    }
    return inst;
}

void parseFile(Instruction **instructions, FILE *file) {
    int currPtr = 0;
    char *str;
    char *cmdStr;
    int ch = 0;
    size_t size = 16;
    size_t len = 0;

    do {        
        str = getStr(file, &ch);
        
        cmdStr = parseStrForLabels(str, currPtr);
        instructions[currPtr] = parseStrForCmd(cmdStr, currPtr);
        if (instructions[currPtr]) {
            currPtr++;
        }
        free(str);
    } while (ch != EOF);
}

void main() {
    int *data = (int *)malloc((1 << 19) * sizeof(int));
    Instruction **instrs = (Instruction **)calloc(1 << 19, sizeof(Instruction *));
    Stack *stack = malloc(sizeof(Stack));
    stack->bottom = malloc((1 << 19) * sizeof(int));
    labelPtr = 0;
    labels = malloc((1 << 19) * sizeof(Label *));
    if (!data || !instrs || !stack || !stack->bottom || !labels) {
        printf("Couldn't allocate memory\n");
        exit(1);
    }
    stack->top = stack->bottom;
    FILE *file = fopen("file.txt", "r");
    if (!file) {
        printf("Couldn't open file\n");
        exit(1);
    };

    parseFile(instrs, file);

    int instrPtr = 0;

    while (strcmp(instrs[instrPtr]->cmd, "ret")) {
        if (!(strcmp(instrs[instrPtr]->cmd, "ld"))) {
            push(stack, data[strToInt(instrs[instrPtr]->arg)]);
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "st")) {
            data[strToInt(instrs[instrPtr]->arg)] = pop(stack);
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "ldc")) {
            push(stack, strToInt(instrs[instrPtr]->arg));
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "add")) {
            int a = pop(stack);
            int b = pop(stack);
            push(stack, a + b);
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "sub")) {
            int a = pop(stack);
            int b = pop(stack);
            push(stack, a - b);
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "cmp")) {
            int a = pop(stack);
            int b = pop(stack);
            push(stack, b);
            push(stack, a);
            int res;
            if (a > b) {
                res = 1;
            }
            else if (a == b) {
                res = 0;
            }
            else {
                res = -1;
            }
            push(stack, res);
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "jmp")) {
            int ptr = searchForLabel(instrs[instrPtr]->arg);
            if (ptr == -1) {
                printf("Label %s wasn't found\n", instrs[instrPtr]->arg);
                exit(0);
            }
            else {
                instrPtr = ptr;
                continue;
            }
        }
        else if (!strcmp(instrs[instrPtr]->cmd, "br")) {
            if (peek(stack) != 0) {
                int ptr = searchForLabel(instrs[instrPtr]->arg);
                if (ptr == -1) {
                    printf("Label %s wasn't found\n", instrs[instrPtr]->arg);
                    exit(0);
                } else {
                    instrPtr = ptr;
                    continue;
                }
            }
        }
        instrPtr++;
    }
    while (stack->top != stack->bottom) {
        printf("%d\n", *stack->top);
        stack->top--;
    }
}
