/*
   Copyright (C) BABEC. All rights reserved.

   SPDX-License-Identifier: Apache-2.0
*/

package main

import (
	"encoding/json"
	"log"
	"strconv"

	"chainmaker.org/chainmaker/contract-sdk-go/v2/pb/protogo"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sandbox"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sdk"
)

const (
	hashType     = "operate"
	hashTypeText = "text"
)

type FactContract struct {
}

// 存证对象
type Fact struct {
	UserId  string `json:"userId"`
	Name    string `json:"name"`
	Hash    string `json:"hash"`
	Type    string `json:"type"`
	Content string `json:"content"`
	TxId    string `json:"txId"`
	Time    int    `json:"time"`
}

func (f *FactContract) InitContract() protogo.Response {
	return sdk.Success([]byte("Init contract success"))
}

func (f *FactContract) UpgradeContract() protogo.Response {
	return sdk.Success([]byte("Upgrade contract success"))
}

func (f *FactContract) InvokeContract(method string) protogo.Response {
	// 获取参数

	switch method {
	case "save":
		return f.save()
	case "findByHash":
		return f.findByHash()
	case "address":
		return f.address()
	default:
		return sdk.Error("invalid method")
	}
}
func (f *FactContract) save() protogo.Response {
	params := sdk.Instance.GetArgs()

	// 获取参数
	userId := string(params["userId"])
	name := string(params["name"])
	hash := string(params["hash"])
	hashType := "operate"
	content := string(params["content"])
	timeStr := string(params["time"])
	txId, _ := sdk.Instance.GetTxId()

	time, err := strconv.Atoi(timeStr)
	if err != nil {
		msg := "[contract]  time is [" + timeStr + "] not int"
		sdk.Instance.Errorf(msg)
		return sdk.Error(msg)
	}
	if len(hash) == 0 {
		msg := "[contract]  param [file_hash] not found"
		sdk.Instance.Errorf(msg)
		return sdk.Error(msg)
	}
	if hashType != hashTypeText && hashType != hashType {
		msg := "[contract]  param [file_hash] is error"
		sdk.Instance.Errorf(msg)
		return sdk.Error(msg)
	}
	if len(userId) == 0 {
		userId, _ = sdk.Instance.Origin()
	}

	// 查询结果
	result, err := sdk.Instance.GetStateByte("fact_bytes", hash)
	if err != nil {
		return sdk.Error("[contract] failed to call get_state")
	}
	if len(result) > 0 {
		return sdk.Error("[contract] exist file_hash: " + string(result))
	}

	// 构建结构体
	fact := &Fact{
		UserId:  userId,
		Name:    name,
		Hash:    hash,
		Type:    hashType,
		Content: content,
		TxId:    txId,
		Time:    time,
	}

	// 序列化
	factBytes, _ := json.Marshal(fact)

	// 发送事件
	sdk.Instance.EmitEvent("fact", []string{fact.UserId, fact.Name, fact.TxId, fact.Hash, fact.Type, fact.Content, strconv.Itoa(fact.Time)})

	// 存储数据
	err = sdk.Instance.PutStateByte("fact_bytes", fact.Hash, factBytes)
	if err != nil {
		return sdk.Error("[contract] fail to save fact bytes")
	}

	// 记录日志
	sdk.Instance.Log("[contract] save hash=" + string(factBytes))

	// 返回结果
	return sdk.Success(factBytes)

}

func (f *FactContract) findByHash() protogo.Response {
	// 获取参数
	hash := string(sdk.Instance.GetArgs()["hash"])

	// 查询结果
	result, err := sdk.Instance.GetStateByte("fact_bytes", hash)
	if err != nil {
		return sdk.Error("[contract] failed to call get_state")
	}
	if len(result) == 0 {
		return sdk.Error("[contract] not found file_hash: " + hash)
	}
	// 记录日志
	sdk.Instance.Infof("[contract] findByHash hash=" + string(result))

	// 返回结果
	return sdk.Success(result)
}

func (f *FactContract) address() protogo.Response {
	sender, _ := sdk.Instance.Origin()
	return sdk.Success([]byte(sender))
}

func main() {
	err := sandbox.Start(new(FactContract))
	if err != nil {
		log.Fatal(err)
	}
}
