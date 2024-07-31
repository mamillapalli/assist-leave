{{/*
Expand the name of the chart.
*/}}
{{- define "edi-claim-prepay-api.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "edi-claim-prepay-api.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if .Values.namespace }}
{{- printf "%s-%s" $name .Values.environment | trunc 63 | trimSuffix "-" }}
{{- else}}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "edi-claim-prepay-api.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "edi-claim-prepay-api.labels" -}}
helm.sh/chart: {{ include "edi-claim-prepay-api.chart" . }}
{{ include "edi-claim-prepay-api.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "edi-claim-prepay-api.selectorLabels" -}}
{{ include "edi-claim-prepay-api.appSelectorLabel" . }}
app.kubernetes.io/name: {{ include "edi-claim-prepay-api.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "edi-claim-prepay-api.appSelectorLabel" -}}
app: {{ include "edi-claim-prepay-api.name" . }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "edi-claim-prepay-api.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "edi-claim-prepay-api.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
Create service name for the app
*/}}
{{- define "edi-claim-prepay-api.serviceName" -}}
{{- printf "%s-svc-%s" .Chart.Name .Values.environment | trunc 63 | trimSuffix "-" }}
{{- end }}
