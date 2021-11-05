package main

import (
	"fmt"
	"os"
	"reflect"
	"strconv"
)

func main() {
	inputStaff
	v := invoke(inputYourFunction, os.Args[1:])
	outputStaff
	for _, item := range v {
		fmt.Println(item)
	}
}

func invoke(f interface{}, params []string) []reflect.Value {
	fv := reflect.ValueOf(f)
	ft := reflect.TypeOf(f)
	realParams := make([]reflect.Value, len(params))
	for i, item := range params {
		switch ft.In(i).Kind() {
		case reflect.Int:
			result, _ := strconv.ParseInt(item, 0, 32)
			realParams[i] = reflect.ValueOf(int(result))
			break
		case reflect.String:
			realParams[i] = reflect.ValueOf(item)
			break
		}
	}

	result := fv.Call(realParams)
	return result
}

